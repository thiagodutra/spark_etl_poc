package config.transformations.from

import scala.beans.BeanProperty

class DataSelect {

  @BeanProperty var connection_name = new String()
  @BeanProperty var table = new String()
  @BeanProperty var columns = new java.util.ArrayList[String]()
  @BeanProperty var filters = new Filters()

}

class Filters {

  @BeanProperty var and = new java.util.ArrayList[String]()
  @BeanProperty var or = new java.util.ArrayList[String]()

}
