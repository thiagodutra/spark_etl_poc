package config.transformations

import config.transformations.from.DataSelect
import config.transformations.join.Join

import java.util.Comparator
import scala.beans.BeanProperty
import scala.collection.mutable

class Transformations {

  @BeanProperty var from = new java.util.ArrayList[DataSelect]()
  @BeanProperty var join = new java.util.ArrayList[Join]()
  @BeanProperty var aggregation = new java.util.ArrayList[Aggregation]()

}
