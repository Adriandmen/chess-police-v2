package com.adrianmensing.twitch

import com.adrianmensing.twitch.auth.AuthenticationTokens
import com.adrianmensing.twitch.users.IRCUser
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.{TwitchClient, TwitchClientBuilder}
import matchers.ChessMoveMatcher
import org.slf4j.LoggerFactory

import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import scala.collection.mutable

object Entry extends App {
  final private val MODERATOR_ID = "856077756"
  final private val ClientConfig = AuthenticationTokens

  final private val LOGGER = LoggerFactory.getLogger("CORE")

  final private val client: TwitchClient = TwitchClientBuilder
    .builder()
    .withClientId(ClientConfig.CLIENT_ID)
    .withClientSecret(ClientConfig.CLIENT_SECRET)
    .withEnableHelix(true)
    .withChatAccount(ClientConfig.credential)
    .withEnableChat(true)
    .build()

  final private lazy val chat         = client.getChat
  final private lazy val helix        = client.getHelix
  final private lazy val eventManager = client.getEventManager
  final private val enabled           = new AtomicBoolean(false)

  private val allowed        = mutable.Set.empty[String]
  private val broadcasters   = List("adnan_m1")
  private val broadcasterIds = mutable.Map.empty[String, String]

  private def findUserId(username: String): String = {
    val users = helix.getUsers(ClientConfig.credential.getAccessToken, null, java.util.List.of(username)).execute()
    val user  = users.getUsers.get(0)
    user.getId
  }

  private def handleChannelMessageEvent(event: ChannelMessageEvent): Unit = {
    LOGGER.debug(event.getMessageEvent.getRawMessage)
    val user    = IRCUser.fromEvent(event)
    val message = event.getMessage.trim
    val channel = event.getChannel.getName

    val userHasPermissions = user.canModerate || allowed.contains(user.username)

    if (userHasPermissions && "!c\\s+.+".r.matches(message)) {
      val command = message.replaceFirst("!c", "").trim.toLowerCase(Locale.ROOT)

      command match {
        case "on"                    => enableMoveDetection(channel)
        case "off"                   => disableMoveDetection(channel)
        case "ping"                  => chat.sendMessage(channel, "Pong!")
        case s"temp allow $username" => allowed.add(username); chat.sendMessage(channel, s"Temporarily granted permissions to $username")
        case s"revoke $username"     => allowed.remove(username); chat.sendMessage(channel, s"Revoked permissions from $username")
        case _                       => chat.sendMessage(channel, s"Unrecognized command '$command'.")
      }
    } else if (ChessMoveMatcher.matches(message) && !user.canModerate && enabled.get()) {
      val messageId     = event.getMessageEvent.getMessageId.get()
      val broadcaster   = event.getMessageEvent.getChannelName.get()
      val broadcasterId = broadcasterIds(broadcaster)

      val command = helix.deleteChatMessages(ClientConfig.credential.getAccessToken, broadcasterId, MODERATOR_ID, messageId)
      command.execute()
    }
  }

  final private def enableMoveDetection(channel: String): Unit = {
    val alreadyActive = enabled.getAndSet(true)

    if (!alreadyActive) {
      chat.sendMessage(channel, "Move detection has been activated. Please refrain from giving any hints/moves from this point forward.")
    }
  }

  final private def disableMoveDetection(channel: String): Unit = {
    val stillActive = enabled.getAndSet(false)

    if (stillActive) {
      chat.sendMessage(channel, "Move detection has been deactivated.")
    }
  }

  broadcasters foreach { broadcaster => broadcasterIds.put(broadcaster, findUserId(broadcaster)) }
  broadcasters foreach { chat.joinChannel }
  eventManager.onEvent(classOf[ChannelMessageEvent], handleChannelMessageEvent)
}
