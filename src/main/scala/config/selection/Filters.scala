package config.selection

import scala.beans.BeanProperty

class Filters {

  @BeanProperty var and = new java.util.ArrayList[String]()
  @BeanProperty var or = new java.util.ArrayList[String]()

}
