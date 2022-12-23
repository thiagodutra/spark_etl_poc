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
val scalaLoggingVersion = "3.9.4"
val logBackClassicVersion = "1.2.10"
libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.0.0",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.yaml" % "snakeyaml" % snakeYamlVersion,
  "mysql" % "mysql-connector-java" % mysqlConnectorVersion,
  "org.postgresql" % "postgresql" % postgresVersion,
  "org.mongodb.spark" %% "mongo-spark-connector" % mongoConnectorVersion,
  "ch.qos.logback" % "logback-classic" % logBackClassicVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
)


