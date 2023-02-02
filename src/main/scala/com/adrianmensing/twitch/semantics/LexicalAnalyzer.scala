package com.adrianmensing.twitch.semantics

sealed trait Sym
case class Word(word: String)     extends Sym
case class Number(number: Int)    extends Sym
case class Symbol(symbol: String) extends Sym
