package pellis.testUtils

import org.scalamock.function.{MockFunction2, MockFunction3}
import org.scalamock.scalatest.MockFactory
import pellis.core.Learner
import pellis.model.Command.{LearnGalaxyRomanConversion, LearnMetalCost}
import pellis.model.{GalaxyCypher, MetalCostBook}

trait MockLearner extends Learner {
  self: MockFactory ⇒

  val fakeLearnGalaxyRomanConversion: MockFunction2[LearnGalaxyRomanConversion, GalaxyCypher, GalaxyCypher] =
    mockFunction[LearnGalaxyRomanConversion, GalaxyCypher, GalaxyCypher]("learnGalaxyRomanConversion")

  val fakeLearnMetalCost: MockFunction3[LearnMetalCost, MetalCostBook, GalaxyCypher, MetalCostBook] =
    mockFunction[LearnMetalCost, MetalCostBook, GalaxyCypher, MetalCostBook]("learnMetalCost")

  override protected def learnGalaxyRomanConversion(input: LearnGalaxyRomanConversion, cypher: GalaxyCypher): GalaxyCypher =
    fakeLearnGalaxyRomanConversion(input, cypher)


  override protected def learnMetalCost(input: LearnMetalCost, metalCostBook: MetalCostBook, cypher: GalaxyCypher): MetalCostBook =
    fakeLearnMetalCost(input, metalCostBook, cypher)

}
