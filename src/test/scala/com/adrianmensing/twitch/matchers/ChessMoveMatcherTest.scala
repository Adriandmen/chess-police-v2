package com.adrianmensing.twitch.matchers

import com.adrianmensing.twitch.matchers.ChessMoveMatcher.matches
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ChessMoveMatcherTest extends AnyFlatSpec with should.Matchers {
  "The ChessMoveMatcher" should "be able to detect standalone squares" in {
    matches("e4") shouldBe true
    matches("Ne4") shouldBe true
    matches("Qxe4") shouldBe true
    matches("Axe4") shouldBe true
    matches("Ixe4") shouldBe false
    matches("1.e4") shouldBe true
    matches("1. Nf3") shouldBe true
    matches("exf3") shouldBe true
    matches("exf") shouldBe false
    matches("nne4") shouldBe false
    matches("n x e4") shouldBe true
    matches("xe4") shouldBe false
    matches("e43") shouldBe false
    matches("Ne45") shouldBe false
    matches("he4") shouldBe false
    matches("hxe4") shouldBe true
  }
}
