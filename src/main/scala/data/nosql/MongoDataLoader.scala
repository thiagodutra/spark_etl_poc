package data.nosql

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import config.connection.ConnectionConfig
import config.especification.Especifications
import data.DataLoad
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import utils.ConnectionUtils

import scala.collection.convert.ImplicitConversions.`iterable AsScalaIterable`
import scala.collection.mutable


case class MongoDataLoader() extends DataLoad {

  override def loadData(
                         session: SparkSession,
                         config: Especifications,
                         globalTableMap: collection.mutable.HashMap[String, Dataset[Row]]
                       ): mutable.HashMap[String, Dataset[Row]] = {

    val collectionsMap = globalTableMap

    val noSqlConnections = config.datasource.connections
      .filter(connectionType => ConnectionUtils.isMongoConnection(connectionType.`type`))

    if (noSqlConnections.nonEmpty) {
      noSqlConnections.foreach( connection =>
        connection.collections.foreach ( collection => {
          val uniqueKey = createUniqueTableName(connection.name, collection)
          val dataset = getCollections(session, connection, collection)
          collectionsMap.put(uniqueKey, dataset)
        }))
    }
    collectionsMap
  }

  private def getCollections(sparkSession: SparkSession, connection: ConnectionConfig, collection: String): Dataset[Row] = {
    val readConfig = ReadConfig(Map(
      MongoConstants.MONGO_URI -> getConnectionUrl(collection, connection),
      MongoConstants.READ_PREFERENCE_NAME -> MongoConstants.SECONDARY_PREFERRED,
      MongoConstants.MONGO_INPUT_DATABASE -> connection.database))

    val mongo = MongoSpark.load(sparkSession, readConfig)
    mongo
  }

  private def getConnectionUrl(collection: String, config: ConnectionConfig): String = {
    s"mongodb://${config.host}/${config.database}.$collection"
  }

  private def createUniqueTableName(connectionName: String, collection: String) = {
    val uniqueName = s"${connectionName.replaceAll(" ", "")}_$collection".toUpperCase()
    uniqueName
  }
}

object MongoConstants {
  val DBTYPE = List("mongodb", "mongo")
  val READ_PREFERENCE_NAME = "readPreference.name"
  val SECONDARY_PREFERRED = "secondaryPreferred"
  val MONGO_INPUT_DATABASE = "spark.mongodb.input.database"
  val MONGO_URI = "uri"
}
