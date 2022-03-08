package config.connection

import scala.beans.BeanProperty

class ConnectionConfig {

  @BeanProperty var `type` = new String()
  @BeanProperty var name = new String()
  @BeanProperty var driver = new String()
  @BeanProperty var host = new String()
  @BeanProperty var user = new String()
  @BeanProperty var password = new String()
  @BeanProperty var database = new String()
  @BeanProperty var path = new String()
  @BeanProperty var delimiter = new String()
  @BeanProperty var port = new String()
  @BeanProperty var tables = new java.util.ArrayList[String]()
  @BeanProperty var collections = new java.util.ArrayList[String]()

}
