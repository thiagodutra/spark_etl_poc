package config.transformations

import scala.beans.BeanProperty

class Aggregation {
  @BeanProperty var table = new String()
  @BeanProperty var group_by = new String()
  @BeanProperty var count = new String()
  @BeanProperty var mean = new String()
  @BeanProperty var max = new String()
  @BeanProperty var min = new String()
  @BeanProperty var sum = new String()
  @BeanProperty var avg = new String()
}
