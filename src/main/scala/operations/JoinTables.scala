package operations

import config.especification.Especifications
import config.transformations.Transformations
import org.apache.spark.sql.{Dataset, Row}

import scala.collection.mutable

case class JoinTables(tablesMap: mutable.Map[String, Dataset[Row]], config: Especifications) {

  def join(): mutable.Map[String, Dataset[Row]] = {
    joinTablesAndColumns(this.config.transformations, this.tablesMap)
  }

  private def joinTablesAndColumns(transformation: Transformations, tablesMap: mutable.Map[String, Dataset[Row]]): mutable.Map[String, Dataset[Row]] = {
    if (!transformation.join.isEmpty) {
      var joinedTables: Dataset[Row] = null
      transformation.join.forEach(join => {
        val leftTableProperties = join.tables.get(0)
        //Verify if tables exists
        // val leftTableName = createUniqueTableName(leftTableProperties.connection_name, leftTableProperties.table)
        val leftDataSetTable = tablesMap(leftTableProperties.table)
        for (index <- 1 until join.tables.size()) {
          val rightTableProperties = join.tables.get(index)
          val rightDataSetTable = tablesMap(rightTableProperties.table)
          joinedTables = leftDataSetTable.join(rightDataSetTable, join.column)
        }
        tablesMap.put(join.new_name, joinedTables)
      })
    }
    tablesMap
  }
}
