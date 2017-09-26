package pellis.core

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}
import pellis.core.impl.BasicLearner
import pellis.model.Command
import pellis.testUtils.MockNumberConverterProvider

class BasicLearnerTest extends WordSpec with Matchers with MockFactory{

  private val learner = new BasicLearner with MockNumberConverterProvider with MockFactory

  "#learnGalaxyRomanConversion" should {
    "return the cypher with a new entry" in {
      val cypher = Map("glob" -> "I", "prok" -> "V")
      val input = Command.LearnGalaxyRomanConversion("pish", "X")

      (learner.fakeRomanConverter.validate _).expects("X").returning(true)

      val expected = Map("glob" → "I", "prok" → "V", "pish" → "X")

      val result = learner.learnGalaxyRomanConversion(input, cypher)

      result shouldBe expected
    }

//    "override conversion with the newest version" in {
//      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X")
//      val input = Command.LearnGalaxyRomanConversion("prok", "I")
//
//      (learner.fakeRomanConverter.validate _).expects("I").returning(true).once
//
//      val expected = Map("prok" → "I", "pish" → "X")
//
//      val result = learner.learnGalaxyRomanConversion(input, cypher)
//
//      result shouldBe expected
//    }
//
//    "return the cypher untouched if the roman number is invalid" in {
//      val cypher = Map("glob" → "I", "prok" → "V")
//      val input = Command.LearnGalaxyRomanConversion("prok", "A")
//
//      (learner.fakeRomanConverter.validate _).expects("A").returning(false)
//
//      val expected = Map("glob" → "I", "prok" → "V")
//
//      val result = learner.learnGalaxyRomanConversion(input, cypher)
//
//      result shouldBe expected
//    }
//  }
//
//  "#learnLearnMetalCost" should {
//    "return the MetalCostBook collection with a new entry" in {
//      val cypher = Map.empty[String, String]
//      val metalCostBook = Map("silver" → 5d, "gold" → 10d)
//
//      (learner.fakeGalaxyConverter.toInt _).expects("pish pish", cypher).returning(20)
//
//      val input = Command.LearnMetalCost("pish pish", "iron", 20d)
//      val expected = Map("silver" → 5d, "gold" → 10d, "iron" → 1d)
//
//      val result = learner.learnMetalCost(input, metalCostBook, cypher)
//
//      result shouldBe expected
//    }
//
//    "override exchange rate with the newest value" in {
//      val cypher = Map.empty[String, String]
//      val metalCostBook = Map("silver" → 5d, "gold" → 10d)
//
//      (learner.fakeGalaxyConverter.toInt _).expects("pish pish", cypher).returning(20)
//
//      val input = Command.LearnMetalCost("pish pish", "gold", 20d)
//      val expected = Map("silver" → 5d, "gold" → 1d)
//
//      val result = learner.learnMetalCost(input, metalCostBook, cypher)
//
//      result shouldBe expected
//    }
//
//    "return the cypher untouched if the number is invalid" in {
//      val cypher = Map.empty[String, String]
//      val metalCostBook = Map("silver" → 5d, "gold" → 10d)
//
//      (learner.fakeGalaxyConverter.toInt _).expects("pish glob tegj", cypher).throwing(new NumberFormatException)
//
//      val input = Command.LearnMetalCost("pish glob tegj", "gold", 1d)
//      val expected = Map("silver" → 5d, "gold" → 10d)
//
//      val result = learner.learnMetalCost(input, metalCostBook, cypher)
//
//      result shouldBe expected
//    }
  }
}
