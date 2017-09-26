package pellis.core.impl

import pellis.converter.NumberConverterProvider
import pellis.core.Learner
import pellis.model.{Command, GalaxyCypher, MetalCostBook}

import scala.util.Try

trait BasicLearner extends Learner {
  self: NumberConverterProvider ⇒

  override def learnGalaxyRomanConversion(input: Command.LearnGalaxyRomanConversion, cypher: GalaxyCypher): GalaxyCypher = {
    if (romanConverter.validate(input.romanSymbol))
      cypher.filterNot { case (_, value) ⇒ value == input.romanSymbol } + (input.galaxySymbol.toLowerCase → input.romanSymbol)
    else
      cypher
  }

  override def learnMetalCost(input: Command.LearnMetalCost, metalCostBook: MetalCostBook, cypher: GalaxyCypher): MetalCostBook = {
    val result = Try(galaxyConverter.toInt(input.quantity, cypher)).map(
      number ⇒ metalCostBook + (input.metal → (input.value / number))
    )

    result.getOrElse(metalCostBook)
  }

}
