package com.adrianmensing.twitch.auth.config

/**
  * Configuration Factory trait.
  *
  * This is the base trait that underlying factories should implement.
  * @tparam Config
  *   The type of the data representing object.
  */
trait ConfigurationFactory[Config] {
  def get(): Config
}
