package com.adrianmensing.twitch.matchers

import java.util.Locale

object ChessMoveMatcher {

  private val regexes = List(
    """(^|\W)[a-h][1-8]($|\W)""".r,                                     // squares
    """(^|\W)[kqrbn]\s*[a-h]?\s*[1-8]?\s*x?\s*[a-h]\s*[1-8]($|\W)""".r, // Piece ~ Square
    """(^|\W)[a-h]\s*[1-8]?\s*x\s*[a-h]\s*[1-8]($|\W)""".r,             // pawn ~ Square
    """(o-o(-o)?|0-0(-0)?)""".r                                         // castling
  )

  def matches(content: String): Boolean = {
    val normalizedContent = content.toLowerCase(Locale.ROOT)

    regexes.exists(x => (x findFirstIn normalizedContent).nonEmpty)
  }
}
