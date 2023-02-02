package com.adrianmensing.twitch.semantics

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import com.adrianmensing.twitch.semantics.tokens._

class MessageParserTest extends AnyFlatSpec with should.Matchers {
  private val parser = new MessageParser

  import parser._

  "The MessageParser" should "successfully parse normal moves" in {
    parse("Qxe4") shouldEqual Move(Queen, Square(File("e"), Rank(4)))
  }

  it should "not be able to parse moves with characters prepended to it" in {
    tryParse("AQxe4") shouldBe a[Failure]
  }

  private def parse(content: String): Move = {
    parser.parse(parser.move, content) match {
      case Success(matched, _) => matched
      case _                   => fail()
    }
  }

  private def tryParse(content: String): ParseResult[Move] = {
    parser.parse(parser.move, content)
  }
}
