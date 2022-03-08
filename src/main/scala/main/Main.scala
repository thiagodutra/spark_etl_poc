package main

import config.ConfigFile.loadConfigFile
import data.nosql.MongoDataLoader
import data.relational.RelationalDataLoader
import org.apache.spark.sql.SparkSession

object Main extends App {

  val configFile = loadConfigFile("src/main/resources/config.yml")

  val spark = SparkSession.builder().master("local[*]")
    .getOrCreate()

  val mongo = MongoDataLoader()
  val dataset = mongo.loadData(spark, configFile)

  val relationalDataLoader = RelationalDataLoader()
  val relationalDataSet = relationalDataLoader.loadData(spark, configFile)

  val dataRow = dataset.get("MONGODATABASE_EXAMPLE_1")
  dataRow.get.show()

  spark.stop()
  
}
