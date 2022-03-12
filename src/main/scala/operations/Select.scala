package operations

import config.especification.Especifications
import config.transformations.Transformations
import org.apache.spark.sql.{Dataset, Row}
import utils.DataUtils.createUniqueTableName

import scala.collection.mutable

case class Select(tablesMap: mutable.Map[String, Dataset[Row]], config: Especifications) {

  def select(): mutable.Map[String, Dataset[Row]] = {
    selectColumnsAndFilters(config.transformations, tablesMap)
  }

  private def selectColumnsAndFilters(transformations: Transformations, tablesMap: mutable.Map[String, Dataset[Row]]): mutable.Map[String, Dataset[Row]] = {
    if (!transformations.from.isEmpty) {
      transformations.from.forEach(trans => {
        val tableName = createUniqueTableName(trans.connection_name, trans.table)
        val tableToFilter = tablesMap(tableName)
        val filteredTable = tableToFilter.select(String.join(SelectConstants.COMMA, trans.columns))
        val andExp = String.join(SelectConstants.AND, trans.filters.and)
        val orExp = String.join(SelectConstants.OR, trans.filters.or)
        //      (trans.filters.and.isEmpty, trans.filters.or.isEmpty)
        (Some(trans.filters.and), Some(trans.filters.or)) match {
          case (Some(_), Some(_)) => filteredTable
            .where(andExp)
            .where(orExp)
          case (Some(_), _) => filteredTable
            .where(andExp)
          case (_, Some(_)) => filteredTable
            .where(orExp)
        }
        tablesMap.put(s"filtered_$tableName", filteredTable)
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
