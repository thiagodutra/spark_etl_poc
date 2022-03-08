package operations

import config.especification.Especifications
import config.selection.Transformations
import org.apache.spark.sql.{Dataset, Row}
import utils.DataUtils.createUniqueTableName

import scala.collection.mutable

case class Select() {

  def select(tablesMap: mutable.Map[String, Dataset[Row]], config: Especifications): mutable.Map[String, Dataset[Row]] = {
    selectColumnsAndFilters(config.transformations, tablesMap)

    /**    addGroupBy()
     * Group by returns a special Grouped Table named RelationalGroupedDataSet
     * how to
     */
    //    addJoins()
  }

  private def selectColumnsAndFilters(transformations: Transformations, tablesMap: mutable.Map[String, Dataset[Row]]): mutable.Map[String, Dataset[Row]] = {
    transformations.from.forEach { trans => {
      val tableName = createUniqueTableName(trans.connection_name, trans.table)
      val tableToFilter = tablesMap(tableName)
      val filteredTable = tableToFilter.select(String.join(SelectConstants.COMMA, trans.columns))

//      (trans.filters.and.isEmpty, trans.filters.or.isEmpty)
      (Some(trans.filters.and), Some(trans.filters.or)) match {
        case (Some(_), Some(_)) => filteredTable
          .where(String.join(SelectConstants.AND, trans.filters.and))
          .where(String.join(SelectConstants.OR, trans.filters.or))
        case (Some(_), _) => filteredTable
          .where(String.join(SelectConstants.AND, trans.filters.and))
        case (_, Some(_)) => filteredTable
          .where(String.join(SelectConstants.OR, trans.filters.and))
      }
      tablesMap.put(tableName, filteredTable)
      filteredTable.groupBy("name")
    }
    }
    tablesMap
  }
}

object SelectConstants {
  val AND = "and"
  val OR = "or"
  val COMMA = ", "
}
