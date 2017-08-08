package pellis

import scala.util.{Failure, Success, Try}

object GalaxyGuide {

  private val ERROR_MESSAGE: String = "I have no idea what you are talking about"

  object RegEx {
    val galaxyConversion = """^([a-zA-Z]+) is ([A-Z])$"""
    val exchangeRate = """^([a-zA-Z_ ]+) ([a-zA-Z]+) is (\d+) [c,C]redits$"""
    val howMuch = """^[H,h]ow much is ([a-z_ ]+) \?$"""
    val howManyCredits = """^[H,h]ow many [c,C]redits is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
    val howManyMetals = """^[H,h]ow many ([a-zA-Z_ ]+) is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
  }

  private case class Execution(output: List[String], exchangeRate: ExchangeRate, cypher: GalaxyCypher)

  private object Execution {
    def empty: Execution = Execution(List.empty, Map.empty, Map.empty)
  }

  def compute(input: List[String]): List[String] = {
    val execution: Execution = input.foldLeft(Execution.empty) {
      (execution, line) => execute(execution, line)
    }

    execution.output
  }

  private def execute(execution: Execution, input: String): Execution = input match {
    case line if line.matches(RegEx.galaxyConversion) =>
      execution.copy(cypher = learnRoman(execution.cypher, line))
    case line if line.matches(RegEx.exchangeRate) =>
      execution.copy(exchangeRate = learnExchangeRate(execution.exchangeRate, execution.cypher, line))
    case line if line.matches(RegEx.howMuch) =>
      execution.copy(output = execution.output :+ answerHowMuch(execution.cypher, line))
    case line if line.matches(RegEx.howManyCredits) =>
      execution.copy(output = execution.output :+ answerHowManyCredits(execution.exchangeRate, execution.cypher, line))
    case line if line.matches(RegEx.howManyMetals) =>
      execution.copy(output = execution.output :+ answerHowManyMetals(execution.exchangeRate, execution.cypher, line))
    case line if line.isEmpty | line.trim.length == 0 => execution
    case _ => execution.copy(output = execution.output :+ ERROR_MESSAGE)
  }

  def learnRoman(cypher: GalaxyCypher, input: String): GalaxyCypher = {
    val pattern = RegEx.galaxyConversion.r
    input match {
      case pattern(galaxyNumber, romanNumber) if RomanNumber.validate(romanNumber) =>
        cypher.filterNot { case (_, value) => value == romanNumber } + (galaxyNumber.toLowerCase -> romanNumber)
      case _ => cypher
    }
  }

  def learnExchangeRate(exchangeRate: ExchangeRate, cypher: GalaxyCypher, input: String): ExchangeRate = {
    val pattern = RegEx.exchangeRate.r
    input match {
      case pattern(galaxyNumber, metal, value) =>
        Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)) match {
          case Success(number) =>
            val unitValue = value.toDouble / number
            exchangeRate + (metal.toLowerCase -> unitValue)
          case Failure(_) => exchangeRate
        }
      case _ => exchangeRate
    }
  }

  def answerHowMuch(cypher: GalaxyCypher, input: String): String = {
    val pattern = RegEx.howMuch.r
    input match {
      case pattern(galaxyNumber) =>
        Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)) match {
          case Success(number) => s"$galaxyNumber is $number"
          case Failure(_) => s"Sorry, $galaxyNumber is not a number"
        }
      case _ => ERROR_MESSAGE
    }
  }

  def answerHowManyCredits(exchangeRate: ExchangeRate, cypher: GalaxyCypher, input: String): String = {
    val pattern = RegEx.howManyCredits.r
    input match {
      case pattern(galaxyNumber, metal) =>
        (for {
          unitValue <- exchangeRate.get(metal.toLowerCase)
          number <- Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)).toOption
        } yield f"$galaxyNumber $metal is ${unitValue * number}%.0f Credits") match {
          case Some(answer) => answer
          case None => s"Sorry, can't convert $galaxyNumber $metal in Credits"
        }
      case _ => ERROR_MESSAGE
    }
  }

  def answerHowManyMetals(exchangeRate: ExchangeRate, cypher: GalaxyCypher, input: String): String = {
    val pattern = RegEx.howManyMetals.r
    input match {
      case pattern(metalTo, galaxyNumber, metalFrom) =>
        (for {
          valueFrom <- exchangeRate.get(metalFrom.toLowerCase)
          valueTo <- exchangeRate.get(metalTo.toLowerCase)
          number <- Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)).toOption
        } yield {
          val conversion = ((valueFrom * number)/valueTo).toInt
          s"$galaxyNumber $metalFrom is ${if (conversion > 0) GalaxyNumber.fromInt(conversion.toInt, cypher) else "no"} $metalTo"
        }) match {
          case Some(answer) => answer
          case None => s"Sorry, can't convert $galaxyNumber $metalFrom in $metalTo"
        }
      case _ => ERROR_MESSAGE
    }
  }
}
