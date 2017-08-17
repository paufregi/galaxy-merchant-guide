package pellis

import scala.util.Try

object GalaxyGuide {

  private object Message {

    object error {
      def generic: String = "I have no idea what you are talking about"

      def number(number: String): String = s"Sorry, $number is not a number"

      def exchange(number: String, from: String, to: String): String = s"Sorry, can't convert $number $from in $to"
    }

    object answer {
      def howMuch(galaxyNumber: String, number: Double) = f"$galaxyNumber is $number%.0f"

      def howManyCredits(galaxyNumber: String, from: String, number: Double) =
        f"$galaxyNumber $from is $number%.0f Credits"

      def howManyMetals(galaxyNumber: String, from: String, number: String, to: String) =
        f"$galaxyNumber $from is $number $to"
    }

  }

  private object RegEx {
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
      (execution, line) ⇒ execute(execution, line)
    }

    execution.output
  }

  private def execute(execution: Execution, input: String): Execution = input match {
    case line if line.matches(RegEx.galaxyConversion) ⇒
      execution.copy(cypher = learnRoman(execution.cypher, line))
    case line if line.matches(RegEx.exchangeRate) ⇒
      execution.copy(exchangeRate = learnExchangeRate(execution.exchangeRate, execution.cypher, line))
    case line if line.matches(RegEx.howMuch) ⇒
      execution.copy(output = execution.output :+ answerHowMuch(execution.cypher, line))
    case line if line.matches(RegEx.howManyCredits) ⇒
      execution.copy(output = execution.output :+ answerHowManyCredits(execution.exchangeRate, execution.cypher, line))
    case line if line.matches(RegEx.howManyMetals) ⇒
      execution.copy(output = execution.output :+ answerHowManyMetals(execution.exchangeRate, execution.cypher, line))
    case line if line.isEmpty | line.trim.length == 0 ⇒ execution
    case _ ⇒ execution.copy(output = execution.output :+ Message.error.generic)
  }

  def learnRoman(cypher: GalaxyCypher, input: String): GalaxyCypher = {
    val pattern = RegEx.galaxyConversion.r
    input match {
      case pattern(galaxyNumber, romanNumber) if RomanNumber.validate(romanNumber) ⇒
        cypher.filterNot { case (_, value) ⇒ value == romanNumber } + (galaxyNumber.toLowerCase → romanNumber)
      case _ ⇒ cypher
    }
  }

  def learnExchangeRate(exchangeRate: ExchangeRate, cypher: GalaxyCypher, input: String): ExchangeRate = {
    val pattern = RegEx.exchangeRate.r
    input match {
      case pattern(galaxyNumber, metal, value) ⇒
        val result = Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)).map(
          number ⇒ exchangeRate + (metal.toLowerCase → (value.toDouble / number)))
        result.getOrElse(exchangeRate)
      case _ ⇒ exchangeRate
    }
  }

  def answerHowMuch(cypher: GalaxyCypher, input: String): String = {
    val pattern = RegEx.howMuch.r
    input match {
      case pattern(galaxyNumber) ⇒
        val result = Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)).map(number ⇒
          Message.answer.howMuch(galaxyNumber, number))
        result.getOrElse(Message.error.number(galaxyNumber))
      case _ ⇒ Message.error.generic
    }
  }

  def answerHowManyCredits(exchangeRate: ExchangeRate, cypher: GalaxyCypher, input: String): String = {
    val pattern = RegEx.howManyCredits.r
    input match {
      case pattern(galaxyNumber, metal) ⇒
        val result = for {
          unitValue ← exchangeRate.get(metal.toLowerCase)
          number ← Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)).toOption
        } yield Message.answer.howManyCredits(galaxyNumber, metal, unitValue * number)
        result.getOrElse(Message.error.exchange(galaxyNumber, metal, "Credits"))
      case _ ⇒ Message.error.generic
    }
  }

  def answerHowManyMetals(exchangeRate: ExchangeRate, cypher: GalaxyCypher, input: String): String = {
    val pattern = RegEx.howManyMetals.r

    def conversion(valueFrom: Double, valueTo: Double, value: Double): String = {
      val result = ((valueFrom * value) / valueTo).toInt
      if (result > 0) GalaxyNumber.fromInt(result, cypher) else "no"
    }

    input match {
      case pattern(metalTo, galaxyNumber, metalFrom) ⇒
        val result = for {
          valueFrom ← exchangeRate.get(metalFrom.toLowerCase)
          valueTo ← exchangeRate.get(metalTo.toLowerCase)
          number ← Try(GalaxyNumber.toInt(galaxyNumber.toLowerCase, cypher)).toOption
        } yield Message.answer.howManyMetals(galaxyNumber, metalFrom, conversion(valueFrom, valueTo, number), metalTo)
        result.getOrElse(Message.error.exchange(galaxyNumber, metalFrom, metalTo))
      case _ ⇒ Message.error.generic
    }
  }
}
