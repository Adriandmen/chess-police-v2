package com.adrianmensing.twitch.auth.config

/**
  * Twitch Client Configuration
  */
abstract class TwitchClientConfigurationFactory extends ConfigurationFactory[ClientToken] {
  override def get(): ClientToken
}
