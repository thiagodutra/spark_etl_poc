package utils

object DataUtils {

  def createUniqueTableName(connectionName: String, table: String): String = {
    s"${connectionName.replaceAll(""" """, "")}_$table".toUpperCase()
  }

}
