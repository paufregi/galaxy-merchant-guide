package pellis.core.impl

import pellis.core.CommandReader
import pellis.model.Command

import scala.util.matching.Regex

object BasicCommandReader extends CommandReader {

  private object Patters {

    object String {
      val learnGalaxyRomanConversion = """^([a-zA-Z]+) is ([A-Z])$"""
      val learnMetalCost = """^([a-zA-Z_ ]+) ([a-zA-Z]+) is (\d+) [c,C]redits$"""
      val questionHowMuch = """^[H,h]ow much is ([a-z_ ]+) \?$"""
      val questionHowManyCredits = """^[H,h]ow many [c,C]redits is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
      val questionHowManyMetals = """^[H,h]ow many ([a-zA-Z_ ]+) is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
    }

    object RegEx {
      val learnGalaxyRomanConversion: Regex = String.learnGalaxyRomanConversion.r
      val learnMetalCost: Regex = String.learnMetalCost.r
      val questionHowMuch: Regex = String.questionHowMuch.r
      val questionHowManyCredits: Regex = String.questionHowManyCredits.r
      val questionHowManyMetals: Regex = String.questionHowManyMetals.r
    }

  }

  override def translate(input: String): Command = input match {
    case line if line.matches(Patters.String.learnGalaxyRomanConversion) ⇒ learnGalaxyRomanConversion(line)
    case line if line.matches(Patters.String.learnMetalCost) ⇒ learnMetalCost(line)
    case line if line.matches(Patters.String.questionHowMuch) ⇒ questionHowMuch(line)
    case line if line.matches(Patters.String.questionHowManyCredits) ⇒ questionHowManyCredits(line)
    case line if line.matches(Patters.String.questionHowManyMetals) ⇒ questionHowManyMetals(line)
    case line if line.isEmpty | line.trim.length == 0 ⇒ Command.Empty
    case _ ⇒ Command.Unknown
  }

  private def learnGalaxyRomanConversion(input: String): Command = input match {
    case Patters.RegEx.learnGalaxyRomanConversion(galaxySymbol, romanSymbol) ⇒
      Command.LearnGalaxyRomanConversion(galaxySymbol.toLowerCase, romanSymbol)
    case _ ⇒ Command.Unknown
  }

  private def learnMetalCost(input: String): Command = input match {
    case Patters.RegEx.learnMetalCost(quantity, metal, value) ⇒
      Command.LearnMetalCost(quantity.toLowerCase, metal.toLowerCase, value.toDouble)
    case _ ⇒ Command.Unknown

  }

  private def questionHowMuch(input: String): Command = input match {
    case Patters.RegEx.questionHowMuch(galaxyNumber) ⇒ Command.QuestionHowMuch(galaxyNumber.toLowerCase)
    case _ ⇒ Command.Unknown

  }

  private def questionHowManyCredits(input: String): Command = input match {
    case Patters.RegEx.questionHowManyCredits(galaxyNumber, metal) ⇒
      Command.QuestionHowManyCredits(galaxyNumber.toLowerCase, metal.toLowerCase)
    case _ ⇒ Command.Unknown

  }

  private def questionHowManyMetals(input: String): Command = input match {
    case Patters.RegEx.questionHowManyMetals(metalTo, galaxyNumber, metalFrom) ⇒
      Command.QuestionHowManyMetals(galaxyNumber.toLowerCase, metalFrom.toLowerCase, metalTo.toLowerCase)
    case _ ⇒ Command.Unknown
  }

}
