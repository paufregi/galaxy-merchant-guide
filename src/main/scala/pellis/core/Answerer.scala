package pellis.core

import pellis.model.{Answer, Command, GalaxyCypher, MetalCostBook}

trait Answerer {

  protected def answerHowMuch(input: Command.QuestionHowMuch, cypher: GalaxyCypher): Answer

  protected def answerHowManyCredits(input: Command.QuestionHowManyCredits, metalCostBook: MetalCostBook, cypher: GalaxyCypher): Answer

  protected def answerHowManyMetals(input: Command.QuestionHowManyMetals, metalCostBook: MetalCostBook, cypher: GalaxyCypher): Answer
}
