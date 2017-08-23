package pellis

import scala.util.Try

object GalaxyGuide {

  private object RegEx {
    val galaxyConversion = """^([a-zA-Z]+) is ([A-Z])$"""
    val exchangeRate = """^([a-zA-Z_ ]+) ([a-zA-Z]+) is (\d+) [c,C]redits$"""
    val howMuch = """^[H,h]ow much is ([a-z_ ]+) \?$"""
    val howManyCredits = """^[H,h]ow many [c,C]redits is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
    val howManyMetals = """^[H,h]ow many ([a-zA-Z_ ]+) is ([a-zA-Z_ ]+) ([a-zA-Z_ ]+) \?$"""
  }

  private case class Execution(output: List[String], exchangeRate: MetalCostBook, cypher: GalaxyCypher)

  private object Execution {
    def empty: Execution = Execution(List.empty, Map.empty, Map.empty)
  }

  def compute(input: List[String]): List[String] = {
    val execution: Execution = input.foldLeft(Execution.empty) {
      (execution, line) ⇒ execute(execution, line)
    }

    execution.output
  }

  private def execute(execution: Execution, input: String): Execution = CommandReader.read(input) match {
    case command: Command.LearnGalaxyRomanConversion ⇒
      execution.copy(cypher = learnGalaxyRomanConversion(command, execution.cypher))
    case command: Command.LearnMetalCost ⇒
      execution.copy(exchangeRate = learnMetalCost(command, execution.exchangeRate, execution.cypher))
    case command: Command.QuestionHowMuch ⇒
      execution.copy(output = execution.output :+ answerHowMuch(command, execution.cypher))
    case command: Command.QuestionHowManyCredits ⇒ execution.copy(output = execution.output :+ Message.error.generic)
      execution.copy(output = execution.output :+ answerHowManyCredits(command, execution.exchangeRate, execution.cypher))
    case command: Command.QuestionHowManyMetals ⇒ execution.copy(output = execution.output :+ Message.error.generic)
      execution.copy(output = execution.output :+ answerHowManyMetals(command, execution.exchangeRate, execution.cypher))
    case Command.Empty ⇒ execution
    case _ ⇒ execution.copy(output = execution.output :+ Message.error.generic)
  }

  def learnGalaxyRomanConversion(input: Command.LearnGalaxyRomanConversion, cypher: GalaxyCypher): GalaxyCypher = {
    if (RomanNumber.validate(input.romanSymbol))
      cypher.filterNot { case (_, value) ⇒ value == input.romanSymbol } + (input.galaxySymbol.toLowerCase → input.romanSymbol)
    else
      cypher
  }

  def learnMetalCost(input: Command.LearnMetalCost, metalCostBook: MetalCostBook, cypher: GalaxyCypher): MetalCostBook = {
    val result = Try(GalaxyNumber.toInt(input.quantity, cypher)).map(
      number ⇒ metalCostBook + (input.metal → (input.value / number))
    )

    result.getOrElse(metalCostBook)
  }

  def answerHowMuch(input: Command.QuestionHowMuch, cypher: GalaxyCypher): String = {
    val result = Try(GalaxyNumber.toInt(input.galaxyNumber, cypher)).map(number ⇒
      Message.answer.howMuch(input.galaxyNumber, number))

    result.getOrElse(Message.error.number(input.galaxyNumber))
  }

  def answerHowManyCredits(input: Command.QuestionHowManyCredits, metalCostBook: MetalCostBook, cypher: GalaxyCypher): String = {
    val result = for {
      unitValue ← metalCostBook.get(input.metal)
      number ← Try(GalaxyNumber.toInt(input.galaxyNumber, cypher)).toOption
    } yield Message.answer.howManyCredits(input.galaxyNumber, input.metal, unitValue * number)

    result.getOrElse(Message.error.exchange(input.galaxyNumber, input.metal, "Credits"))
  }

  def answerHowManyMetals(input: Command.QuestionHowManyMetals, metalCostBook: MetalCostBook, cypher: GalaxyCypher): String = {
    def conversion(valueFrom: Double, valueTo: Double, value: Double): String = {
      val result = ((valueFrom * value) / valueTo).toInt
      if (result > 0) GalaxyNumber.fromInt(result, cypher) else "no"
    }

    val result = for {
      valueFrom ← metalCostBook.get(input.metalFrom)
      valueTo ← metalCostBook.get(input.metalTo)
      number ← Try(GalaxyNumber.toInt(input.galaxyNumber, cypher)).toOption
    } yield Message.answer.howManyMetals(input.galaxyNumber, input.metalFrom, conversion(valueFrom, valueTo, number), input.metalTo)

    result.getOrElse(Message.error.exchange(input.galaxyNumber, input.metalFrom, input.metalTo))
  }
}
