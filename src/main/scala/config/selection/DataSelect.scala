package config.selection

import scala.beans.BeanProperty

class DataSelect {

  @BeanProperty var connection_name = new String()
  @BeanProperty var table = new String()
  @BeanProperty var columns = new java.util.ArrayList[String]()
  @BeanProperty var filters = new Filters()

}
