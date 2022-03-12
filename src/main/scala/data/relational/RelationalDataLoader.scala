package data.relational

import config.connection.ConnectionConfig
import config.especification.Especifications
import data.DataLoad
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import utils.ConnectionUtils
import utils.DataUtils.createUniqueTableName

import scala.collection.convert.ImplicitConversions.`iterable AsScalaIterable`
import scala.collection.mutable

case class RelationalDataLoader() extends DataLoad {

  override def loadData(
                         session: SparkSession,
                         config: Especifications,
                         globalTableMap: collection.mutable.HashMap[String, Dataset[Row]]
                       ): mutable.Map[String, Dataset[Row]] = {

    val collectionsMap = globalTableMap

    val relationalConnections = config.datasource.connections
      .filter(connectionType => ConnectionUtils.isRelationalConnection(connectionType.`type`))

    if (relationalConnections.nonEmpty) {
      relationalConnections.foreach( connection =>
        connection.tables.foreach ( table => {
          val uniqueKey = createUniqueTableName(connection.name, table)
          val dataset = getTables(session, connection, table)
          collectionsMap.put(uniqueKey, dataset)
        }))
    }
    collectionsMap
  }

  private def getTables(sparkSession: SparkSession, connection: ConnectionConfig, table: String): Dataset[Row] = {
    val url = ConnectionUtils.getRelationalConnectionUrl(connection)
    val options = Map(
      RelationalConstants.URL -> url,
      RelationalConstants.TABLE -> table,
      RelationalConstants.USER -> connection.user,
      RelationalConstants.PASSWORD -> connection.password,
      RelationalConstants.DRIVER -> connection.driver
    )
    var dataframe: Dataset[Row] = null

    try dataframe = sparkSession.read
        .format(RelationalConstants.FORMAT)
        .options(options)
        .load()
    catch {
      case e:Exception => e.printStackTrace()
    }
    dataframe
  }
}

object RelationalDbTypes extends Enumeration {
  type DB = Value
  val MySQL: RelationalDbTypes.Value = Value(1, "mysql")
  val Postgre: RelationalDbTypes.Value = Value(2, "postgre")
  //Remover singular
  val Postgres: RelationalDbTypes.Value = Value(3, "postgres")
  //Adiconar oracle
}

object RelationalConstants {
  var DBTYPES = Seq("mysql", "postgre")
  var FORMAT = "jdbc"
  var URL = "url"
  var TABLE = "dbtable"
  var USER = "user"
  var PASSWORD = "password"
  var DRIVER = "driver"
}
