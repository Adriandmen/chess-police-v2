package com.adrianmensing.twitch.semantics

import com.adrianmensing.twitch.semantics.tokens.{File, Move, Pawn, Piece, Rank, Square}

import scala.annotation.unused
import scala.util.parsing.combinator.RegexParsers

/**
  * For future usage.
  */
@unused
class MessageParser extends RegexParsers {
  def takes: Parser[String]  = """[xX]|takes|captures""".r
  def piece: Parser[Piece]   = """[KQRBNkqrbn]""".r              ^^ { Piece(_) }
  def pawn: Parser[Piece]    = """[A-Ha-h]""".r                  ^^ { _ => Pawn }
  def file: Parser[File]     = """[A-Ha-h]""".r                  ^^ { File }
  def rank: Parser[Rank]     = """[1-8]""".r                     ^^ { rank => Rank(rank.toInt) }
  def square: Parser[Square] = file ~ rank                       ^^ { case x ~ y => Square(x, y) }
  def move: Parser[Move]     = (piece | pawn) ~ takes.? ~ square ^^ { case p ~ _ ~ to => Move(p, to) }
}
