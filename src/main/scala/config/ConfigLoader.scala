package config

import config.especification.Especifications
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

import java.io.{File, FileInputStream}

object ConfigFile {
  def loadConfigFile(path: String): Especifications = {
    val inputStream = new FileInputStream(new File(path))
    val yaml = new Yaml(new Constructor(classOf[Especifications]))
    yaml.load(inputStream).asInstanceOf[Especifications]
  }
}
