package operations

import config.especification.Especifications
import config.transformations.Transformations
import org.apache.spark.sql.{Dataset, Row}
import utils.DataUtils.createUniqueTableName

import scala.collection.convert.ImplicitConversions.`iterable AsScalaIterable`
import scala.collection.mutable

case class Select(tablesMap: mutable.Map[String, Dataset[Row]], config: Especifications) {

  def select(): mutable.Map[String, Dataset[Row]] = {
    selectColumnsAndFilters(config.transformations, tablesMap)
  }

  private def selectColumnsAndFilters(transformations: Transformations, tablesMap: mutable.Map[String, Dataset[Row]]): mutable.Map[String, Dataset[Row]] = {
    if (!transformations.from.isEmpty) {
      transformations.from.forEach(trans => {
        val newTableName = trans.new_name
        val tableName = createUniqueTableName(trans.connection_name, trans.table)
        val tableToFilter = tablesMap(tableName)
        val columnsToFetch = trans.columns.toIndexedSeq
        var filteredTable = tableToFilter.selectExpr(columnsToFetch: _*)
        val andExp = String.join(SelectConstants.AND, trans.filters.and)
        val orExp = String.join(SelectConstants.OR, trans.filters.or)
        //      (trans.filters.and.isEmpty, trans.filters.or.isEmpty)
        (!trans.filters.and.isEmpty, !trans.filters.or.isEmpty)  match {
          case (true, true) => filteredTable
            .where(andExp)
            .where(orExp)
          case (true, false) => filteredTable
            .where(andExp)
          case (false, true) => filteredTable
            .where(orExp)
          case (false, false) => filteredTable
        }
        tablesMap.put(newTableName, filteredTable)
      })
    }
    tablesMap
  }
}


object SelectConstants {
  val AND = "and"
  val OR = "or"
  val COMMA = ", "
}
