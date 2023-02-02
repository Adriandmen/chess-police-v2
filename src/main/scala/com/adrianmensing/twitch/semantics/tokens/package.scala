package com.adrianmensing.twitch.semantics

package object tokens {
  sealed trait Token

  sealed abstract class Piece extends Token
  object Piece {
    def apply(letter: String): Piece = fromChar(letter.head)

    private def fromChar(letter: Char): Piece = letter.toLower match {
      case 'k' => King
      case 'q' => Queen
      case 'r' => Rook
      case 'b' => Bishop
      case 'n' => Knight
      case _   => ???
    }
  }

  case object King   extends Piece
  case object Queen  extends Piece
  case object Rook   extends Piece
  case object Bishop extends Piece
  case object Knight extends Piece
  case object Pawn   extends Piece

  case class File(file: String)             extends Token
  case class Rank(rank: Int)                extends Token
  case class Square(file: File, rank: Rank) extends Token
  case class Move(piece: Piece, to: Square) extends Token
}
