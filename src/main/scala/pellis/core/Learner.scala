package pellis.core

import pellis.model.{Command, GalaxyCypher, MetalCostBook}

trait Learner {

  protected def learnGalaxyRomanConversion(input: Command.LearnGalaxyRomanConversion, cypher: GalaxyCypher): GalaxyCypher

  protected def learnMetalCost(input: Command.LearnMetalCost, metalCostBook: MetalCostBook, cypher: GalaxyCypher): MetalCostBook
}
