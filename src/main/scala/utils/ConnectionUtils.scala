package utils

import config.connection.ConnectionConfig
import data.csv.CSVConstants
import data.nosql.MongoConstants
import data.relational.{RelationalConstants, RelationalDbTypes}

object ConnectionUtils {

  def isCSVConnection(connectionType: String): Boolean = connectionType match {
    case connectionType => CSVConstants.CSVTYPE.contains(connectionType)
  }

  def isRelationalConnection(connectionType: String): Boolean = connectionType match {
    case connectionType => RelationalConstants.DBTYPES.contains(connectionType)
  }

  def isMongoConnection(connectionType: String): Boolean = connectionType match {
    case connectionType => MongoConstants.DBTYPE.contains(connectionType)
  }

  def getRelationalConnectionUrl(config: ConnectionConfig): String = {
    var dbType = config.`type`.toLowerCase()
    RelationalDbTypes.withName(dbType) match {
      case RelationalDbTypes.MySQL => dbType
      case RelationalDbTypes.Postgre => dbType = s"${dbType}sql"
      case RelationalDbTypes.Postgres => dbType = s"${dbType}ql"
    }
    s"jdbc:$dbType://${config.host}:${config.port}/${config.database}"
  }
}
