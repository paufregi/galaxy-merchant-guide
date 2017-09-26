package pellis.testUtils

import org.scalamock.function.{MockFunction2, MockFunction3}
import org.scalamock.scalatest.MockFactory
import pellis.core.Answerer
import pellis.model.Command._
import pellis.model.{Answer, GalaxyCypher, MetalCostBook}

trait MockAnswerer extends Answerer {
  self: MockFactory ⇒

  val fakeAnswerHowMuch: MockFunction2[QuestionHowMuch, GalaxyCypher, Answer] =
    mockFunction[QuestionHowMuch, GalaxyCypher, Answer]("answerHowMuch")

  val fakeAnswerHowManyCredits: MockFunction3[QuestionHowManyCredits, MetalCostBook, GalaxyCypher, Answer] =
    mockFunction[QuestionHowManyCredits, MetalCostBook, GalaxyCypher, Answer]("answerHowManyCredits")

  val fakeAnswerHowManyMetals: MockFunction3[QuestionHowManyMetals, MetalCostBook, GalaxyCypher, Answer] =
    mockFunction[QuestionHowManyMetals, MetalCostBook, GalaxyCypher, Answer]("answerHowManyMetals")

  override protected def answerHowMuch(input: QuestionHowMuch, cypher: GalaxyCypher): Answer =
    fakeAnswerHowMuch(input, cypher)

  override protected def answerHowManyCredits(input: QuestionHowManyCredits, metalCostBook: MetalCostBook, cypher: GalaxyCypher): Answer =
    fakeAnswerHowManyCredits(input, metalCostBook, cypher)

  override protected def answerHowManyMetals(input: QuestionHowManyMetals, metalCostBook: MetalCostBook, cypher: GalaxyCypher): Answer =
    fakeAnswerHowManyMetals(input, metalCostBook, cypher)
}
