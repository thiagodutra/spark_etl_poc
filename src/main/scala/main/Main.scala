package main

import config.ConfigFile.loadConfigFile
import data.nosql.MongoDataLoader
import data.relational.{RelationalDataLoader, RelationalDataSink}
import operations.{JoinTables, Select}
import org.apache.spark.sql.{Dataset, Row, SparkSession}

object Main extends App {

  val configFile = loadConfigFile("src/main/resources/config.yml")
  val globalTablesMap = new collection.mutable.HashMap[String, Dataset[Row]]()
  val spark = SparkSession.builder().master("local[*]")
    .getOrCreate()

  val mongoDataset = MongoDataLoader().loadData(spark, configFile, globalTablesMap)

  val relationalDataSet = RelationalDataLoader().loadData(spark, configFile, globalTablesMap)

  JoinTables(
    Select(
      globalTablesMap,
      configFile).select(), configFile)
    .join()

  RelationalDataSink().sinkData(spark,configFile, globalTablesMap)


  spark.stop()
  
}
