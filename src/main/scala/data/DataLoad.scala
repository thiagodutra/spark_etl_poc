package data

import config.especification.Especifications
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import scala.collection.mutable

trait DataLoad {

  def loadData(session: SparkSession, config: Especifications): mutable.Map[String, Dataset[Row]]

}
