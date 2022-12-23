package data.csv

import com.typesafe.scalalogging.StrictLogging
import config.connection.ConnectionConfig
import config.especification.Especifications
import data.DataLoad
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import utils.ConnectionUtils
import utils.DataUtils.createUniqueTableName

import scala.collection.convert.ImplicitConversions.`iterable AsScalaIterable`
import scala.collection.mutable
import scala.reflect.io.File

case class CSVDataLoader() extends DataLoad with StrictLogging {
  override def loadData(
                         session: SparkSession,
                         config: Especifications,
                         globalMap: mutable.HashMap[String, Dataset[Row]]
                       ): mutable.Map[String, Dataset[Row]] = {


    val collectionsMap = globalMap

    val csvConnection = config.datasource.connections
      .filter(connectionType => ConnectionUtils.isCSVConnection(connectionType.`type`))

    if (csvConnection.nonEmpty) {
      csvConnection.foreach(csvConnection => {
        logger.info(s"Starting to import CSV file on ${csvConnection.path}")
        val uniqueKey = createUniqueTableName(csvConnection.name, CSVConstants.CSVTYPE)
        val dataset = loadCsvData(session, csvConnection)
        collectionsMap.put(uniqueKey, dataset)
      })
    }
    collectionsMap
  }


  private def loadCsvData(session: SparkSession, config: ConnectionConfig): Dataset[Row] = {
    if (verifyPath(config.path)) {
      val options = Map(
        CSVConstants.HEADER -> "true",
        CSVConstants.INFER_SCHEMA -> "true"
      )

      var dataframe: Dataset[Row] = null

      try dataframe = session.read.format(CSVConstants.CSVTYPE)
        .options(options)
        .load(config.path)
      catch {
        case e: Exception => e.printStackTrace()
      }
      dataframe
    } else {
      throw new Exception("File is not CSV or file doesn't exists on informed path")
    }
  }

  private def verifyPath(path: String): Boolean = {
    path.endsWith(".csv") && File(path).exists
  }
}

object CSVConstants {
  val CSVTYPE = "csv"
  val HEADER = "header"
  val INFER_SCHEMA = "inferSchema"
  val CSV_PATH = "csvPath"
}
