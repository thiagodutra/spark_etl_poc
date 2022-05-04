package utils

object DataUtils {

  def createUniqueTableName(connectionName: String, table: String): String = {
    s"${connectionName.replaceAll(""" """, "")}_${removeSchemaFromTableName(table)}".toUpperCase()
  }

  def removeSchemaFromTableName(tableName: String): String = {
    tableName.split("\\.").last
  }

}
