package com.adrianmensing.twitch.auth.config.impl

import com.adrianmensing.twitch.auth.config.impl.LocalTwitchClientConfigFactory.parseLines
import com.adrianmensing.twitch.auth.config.{ClientToken, TwitchClientConfigurationFactory}

import scala.annotation.tailrec
import scala.io.Source

/**
  * Configuration factory that reads from a specific local file.
  */
class LocalTwitchClientConfigFactory extends TwitchClientConfigurationFactory {
  final private[this] val CONFIG_FILE_NAME = "application.conf"

  override def get(): ClientToken = {
    val source = Source.fromFile(CONFIG_FILE_NAME)
    val contents =
      try source.getLines()
      finally source.close()

    parseLines(contents)
  }
}

object LocalTwitchClientConfigFactory {
  private def parseLines(lines: Iterator[String]): ClientToken = parseLines(lines, ClientToken(null, null))

  @tailrec
  private[this] def parseLines(lines: Iterator[String], clientToken: ClientToken): ClientToken = lines.nextOption() match {
    case Some(s"client_id=$value")     => parseLines(lines, clientToken.copy(clientId = value))
    case Some(s"client_secret=$value") => parseLines(lines, clientToken.copy(clientSecret = value))
    case Some(_)                       => parseLines(lines, clientToken) // ignore value
    case None                          => clientToken
  }
}
