package pellis.core.impl

import pellis.converter.NumberConverterProvider
import pellis.core.Answerer
import pellis.model._

import scala.util.Try

trait BasicAnswerer extends Answerer {
  self: NumberConverterProvider ⇒

  override def answerHowMuch(input: Command.QuestionHowMuch, cypher: GalaxyCypher): Answer = {
    val result = Try(galaxyConverter.toInt(input.galaxyNumber, cypher)).map(number ⇒
      Answer.HowMuch(input.galaxyNumber, number))

    result.getOrElse(Answer.NumberError(input.galaxyNumber))
  }

  override def answerHowManyCredits(input: Command.QuestionHowManyCredits, metalCostBook: MetalCostBook, cypher: GalaxyCypher): Answer = {
    val result = for {
      unitValue ← metalCostBook.get(input.metal)
      number ← Try(galaxyConverter.toInt(input.quantity, cypher)).toOption
    } yield Answer.HowManyCredits(input.quantity, input.metal, unitValue * number)

    result.getOrElse(Answer.ExchangeError(input.quantity, input.metal, "Credits"))
  }

  override def answerHowManyMetals(input: Command.QuestionHowManyMetals, metalCostBook: MetalCostBook, cypher: GalaxyCypher): Answer = {
    val result = for {
      valueFrom ← metalCostBook.get(input.metalFrom)
      valueTo ← metalCostBook.get(input.metalTo)
      number ← Try(galaxyConverter.toInt(input.quantity, cypher)).toOption
    } yield {
      val value = ((valueFrom * number) / valueTo).toInt
      val valueString = if (value > 0) galaxyConverter.fromInt(value, cypher) else "no"

      Answer.HowManyMetals(input.quantity, input.metalFrom, valueString, input.metalTo)
    }

    result.getOrElse(Answer.ExchangeError(input.quantity, input.metalFrom, input.metalTo))
  }

}
