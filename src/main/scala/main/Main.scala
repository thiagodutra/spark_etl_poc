package main

import config.ConfigLoader.loadConfigFile
import data.csv.CSVDataLoader
import data.nosql.MongoDataLoader
import data.relational.{RelationalDataLoader, RelationalDataSink}
import org.apache.spark.sql.{Dataset, Row, SparkSession}

import java.io.FileNotFoundException

object Main extends App {

  val path = args(0)
  try {
    val configFile = loadConfigFile(path)
    val globalTablesMap = new collection.mutable.HashMap[String, Dataset[Row]]()
    val spark = SparkSession.builder().master("local[*]")
      .getOrCreate()
    val mongoDataset = MongoDataLoader().loadData(spark, configFile, globalTablesMap)
    val relationalDataSet = RelationalDataLoader().loadData(spark, configFile, globalTablesMap)
    val csvDataset = CSVDataLoader().loadData(spark, configFile, globalTablesMap)
    spark.stop()
  } catch {
    case fnf: FileNotFoundException => println("File not found for the provided path")
    case _: Throwable => println("Something weird occurred")
  }
  //    JoinTables(
  //      Select(
  //        globalTablesMap,
  //        configFile).select(), configFile)
  //      .join()
}
