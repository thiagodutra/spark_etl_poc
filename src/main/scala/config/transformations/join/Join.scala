package config.transformations.join

import scala.beans.BeanProperty

class Join {

  @BeanProperty var column = new String()
  @BeanProperty var tables = new java.util.ArrayList[TableJoinProperties]()
  @BeanProperty var new_name = new String()

}

class TableJoinProperties{
  @BeanProperty var connection_name = new String()
  @BeanProperty var table = new String()
}
