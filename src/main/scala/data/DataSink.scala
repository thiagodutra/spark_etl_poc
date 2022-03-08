package data

import config.especification.Especifications
import org.apache.spark.sql.{Dataset, Row, SparkSession}

trait DataSink {

  def sinkData(session: SparkSession, config: Especifications, tables: Map[String, Dataset[Row]]): Unit

}
