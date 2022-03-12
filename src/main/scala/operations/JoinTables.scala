package operations

import config.especification.Especifications
import config.transformations.Transformations
import org.apache.orc.impl.ConvertTreeReaderFactory.StringGroupFromAnyIntegerTreeReader
import org.apache.spark.sql.{Dataset, Row}
import utils.DataUtils.createUniqueTableName

import java.util
import scala.collection.mutable

case class JoinTables(tablesMap: mutable.Map[String, Dataset[Row]], config: Especifications) {

  def join(): mutable.Map[String, Dataset[Row]] = {
    tablesMap
  }

  private def joinTablesAndColumns(transformation: Transformations, tablesMap: mutable.Map[String, Dataset[Row]]): mutable.Map[String, Dataset[Row]] = {
    if (!transformation.join.isEmpty) {
      val newJoinedTablesName: util.ArrayList[String] = new util.ArrayList[String]();
      var joinedTables: Dataset[Row] = null
      transformation.join.forEach(join => {
        val leftTableProperties = join.tables.get(0)
        val leftTableName = createUniqueTableName(leftTableProperties.connection_name, leftTableProperties.table)
        val leftDataSetTable = tablesMap(leftTableName)
        newJoinedTablesName.add(leftTableName)
        for (index <- 1 until join.tables.size()) {
          val rightTableProperties = join.tables.get(index)
          val rightTableKey = createUniqueTableName(rightTableProperties.connection_name, rightTableProperties.table)
          newJoinedTablesName.add(rightTableKey)
          val rightDataSetTable = tablesMap(rightTableKey)
          joinedTables = leftDataSetTable.join(rightDataSetTable, join.column)
        }
        tablesMap.put(String.join("_", newJoinedTablesName), joinedTables)
      })

    }
    tablesMap
  }
}
