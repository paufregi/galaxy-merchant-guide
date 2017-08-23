package pellis

object CommandReader {

  private object RegEx {
    val learnGalaxyRomanConversion = """^([a-zA-Z]+) is ([A-Z])$"""
    val learnMetalCost = """^([a-zA-Z_ ]+) ([a-zA-Z]+) is (\d+) [c,C]redits$"""
    val questionHowMuch = """^[H,h]ow much is ([a-z_ ]+) \?$"""
    val questionHowManyCredits = """^[H,h]ow many [c,C]redits is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
    val questionHowManyMetals = """^[H,h]ow many ([a-zA-Z_ ]+) is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
  }

  def read(input: String): Command = input match {
    case line if line.matches(RegEx.learnGalaxyRomanConversion) ⇒ learnGalaxyRomanConversion(line)
    case line if line.matches(RegEx.learnMetalCost) ⇒ learnMetalCost(line)
    case line if line.matches(RegEx.questionHowMuch) ⇒ questionHowMuch(line)
    case line if line.matches(RegEx.questionHowManyCredits) ⇒ questionHowManyCredits(line)
    case line if line.matches(RegEx.questionHowManyMetals) ⇒ questionHowManyMetals(line)
    case line if line.isEmpty | line.trim.length == 0 ⇒ Command.Empty
    case _ ⇒ Command.Unknown
  }

  private def learnGalaxyRomanConversion(input: String): Command = {
    val pattern = RegEx.learnGalaxyRomanConversion.r
    input match {
      case pattern(galaxySymbol, romanSymbol) ⇒
        Command.LearnGalaxyRomanConversion(galaxySymbol.toLowerCase, romanSymbol)
      case _ ⇒ Command.Unknown
    }
  }

  private def learnMetalCost(input: String): Command = {
    val pattern = RegEx.learnMetalCost.r
    input match {
      case pattern(quantity, metal, value) ⇒
        Command.LearnMetalCost(metal.toLowerCase, quantity.toLowerCase, value.toDouble)
      case _ ⇒ Command.Unknown
    }
  }

  private def questionHowMuch(input: String): Command = {
    val pattern = RegEx.questionHowMuch.r
    input match {
      case pattern(galaxyNumber) ⇒ Command.QuestionHowMuch(galaxyNumber.toLowerCase)
      case _ ⇒ Command.Unknown
    }
  }

  private def questionHowManyCredits(input: String): Command = {
    val pattern = RegEx.questionHowManyCredits.r
    input match {
      case pattern(galaxyNumber, metal) ⇒
        Command.QuestionHowManyCredits(galaxyNumber.toLowerCase, metal.toLowerCase)
      case _ ⇒ Command.Unknown
    }
  }

  private def questionHowManyMetals(input: String): Command = {
    val pattern = RegEx.questionHowManyMetals.r
    input match {
      case pattern(metalTo, galaxyNumber, metalFrom) ⇒
        Command.QuestionHowManyMetals(galaxyNumber.toLowerCase, metalFrom.toLowerCase, metalTo.toLowerCase)
      case _ ⇒ Command.Unknown
    }
  }
}
