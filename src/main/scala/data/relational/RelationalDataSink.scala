package data.relational

import config.connection.ConnectionConfig
import config.especification.Especifications
import data.DataSink
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import utils.ConnectionUtils

import java.util.Properties
import scala.collection.convert.ImplicitConversions.`iterable AsScalaIterable`

case class RelationalDataSink() extends DataSink {
  override def sinkData(session: SparkSession, config: Especifications, tables: Map[String, Dataset[Row]]): Unit = {
    val sink = config.datasink.connections
      .filter(connectionType => ConnectionUtils.isRelationalConnection(connectionType.`type`))

    sink.foreach(datasink =>
      datasink.getTables.foreach(table => {
          val sinkUrl = ConnectionUtils.getRelationalConnectionUrl(datasink)
          val sinkProperties = createSinkProperties(datasink)
          tables("tableName").write.jdbc(sinkUrl, table, sinkProperties)
      }))
  }

  private def createSinkProperties(config: ConnectionConfig): Properties = {
    val properties = new Properties()
    properties.setProperty("DRIVER", config.driver)
    properties.setProperty("USER", config.user)
    properties.setProperty("PASSWORD", config.password)
    properties
  }
}