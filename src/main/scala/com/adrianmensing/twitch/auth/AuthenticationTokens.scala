package com.adrianmensing.twitch.auth

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential

object AuthenticationTokens {
  final val CLIENT_ID        = System.getenv("CLIENT_ID")
  final val CLIENT_SECRET    = System.getenv("CLIENT_SECRET")
  final val APP_ACCESS_TOKEN = System.getenv("APP_ACCESS_TOKEN")
  final val REFRESH_TOKEN    = System.getenv("REFRESH_TOKEN")

  val credential = new OAuth2Credential("twitch", APP_ACCESS_TOKEN)
  credential.setRefreshToken(REFRESH_TOKEN)
}
