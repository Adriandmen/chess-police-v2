package com.adrianmensing.twitch.auth

import com.adrianmensing.twitch.auth.AuthenticationTokens.{CLIENT_ID, CLIENT_SECRET}
import com.github.twitch4j.auth.providers.TwitchIdentityProvider

object Credentials {
  val identityProvider = new TwitchIdentityProvider(CLIENT_ID, CLIENT_SECRET, "http://localhost:3000")
}
