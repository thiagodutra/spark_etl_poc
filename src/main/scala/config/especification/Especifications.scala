package config.especification

import config.data.Data
import config.transformations.Transformations

import scala.beans.BeanProperty

class Especifications {
  @BeanProperty var datasource = new Data()
  @BeanProperty var transformations = new Transformations()
  @BeanProperty var datasink = new Data()
}
