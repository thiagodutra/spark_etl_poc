package data

import config.especification.Especifications
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import scala.collection.mutable

trait DataSink {

  def sinkData(session: SparkSession, config: Especifications, tables: mutable.Map[String, Dataset[Row]]): Unit

}
