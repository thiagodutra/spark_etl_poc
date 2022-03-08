ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.13"

lazy val root = (project in file("."))
  .settings(
    name := "spark_002"
  )

val sparkVersion = "3.2.0"
val snakeYamlVersion = "1.29"
val mysqlConnectorVersion = "8.0.26"
val postgresVersion = "42.3.1"
val mongoConnectorVersion = "3.0.1"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.0.0",
  "org.apache.spark" %% "spark-core" % "3.2.0",
  "org.apache.spark" %% "spark-sql" % "3.2.0" % "provided",
  "org.yaml" % "snakeyaml" % "1.29",
  "mysql" % "mysql-connector-java" % "8.0.26",
  "org.postgresql" % "postgresql" % "42.3.1",
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1"
)


