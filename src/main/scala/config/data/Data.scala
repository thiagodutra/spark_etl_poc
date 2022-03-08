package config.data

import config.connection.ConnectionConfig

import scala.beans.BeanProperty

class Data {

  @BeanProperty var connections = new java.util.ArrayList[ConnectionConfig]()

}
