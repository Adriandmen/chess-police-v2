package com.adrianmensing.twitch.users

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.common.enums.CommandPermission
import com.github.twitch4j.common.enums.CommandPermission.{BROADCASTER, MODERATOR}

import scala.jdk.CollectionConverters.CollectionHasAsScala

case class IRCUser(username: String, permissions: Set[CommandPermission]) {
  final val isModerator   = permissions contains MODERATOR
  final val isBroadcaster = permissions contains BROADCASTER

  final def canModerate: Boolean = isModerator || isBroadcaster
}

object IRCUser {
  final def fromEvent(event: ChannelMessageEvent): IRCUser = {
    val username = event.getUser.getName
    IRCUser(username, event.getPermissions.asScala.toSet)
  }
}
