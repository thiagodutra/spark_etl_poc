package operations

import config.especification.Especifications
import config.transformations.Transformations
import org.apache.spark.sql.{Dataset, Row}

import scala.collection.mutable

class Aggregation(tablesMap: mutable.Map[String, Dataset[Row]], config: Especifications){

//  def aggregate(): mutable.Map[String, Dataset[Row]] = {
//
//  }

  private def doAggregate(transformations: Transformations, tablesMap: mutable.Map[String, Dataset[Row]]) = {
    if (!transformations.aggregation.isEmpty){

    }
    tablesMap
  }

}


//  table: "directors"
//  group_by: column_name
//  count: true/false
//  mean: column_name
//  max: column_name
//  min: column_name
//  sum: column_name
//  avg: column_name