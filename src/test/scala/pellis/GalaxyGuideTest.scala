package pellis

import org.scalatest.{Matchers, WordSpec}

class GalaxyGuideTest extends WordSpec with Matchers {

  "#learnGalaxyRomanConversion" should {
    "return the cypher with a new entry" in {
      val cypher = Map("glob" → "I", "prok" → "V")
      val input = Command.LearnGalaxyRomanConversion("pish", "X")

      val expected = Map("glob" → "I", "prok" → "V", "pish" → "X")

      GalaxyGuide.learnGalaxyRomanConversion(input, cypher) shouldBe expected
    }

    "override conversion with the newest version" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X")
      val input = Command.LearnGalaxyRomanConversion("prok", "I")

      val expected = Map("prok" → "I", "pish" → "X")

      GalaxyGuide.learnGalaxyRomanConversion(input, cypher) shouldBe expected
    }

    "return the cypher untouched if the roman number is invalid" in {
      val cypher = Map("glob" → "I", "prok" → "V")
      val input = Command.LearnGalaxyRomanConversion("prok", "A")

      val expected = Map("glob" → "I", "prok" → "V")

      GalaxyGuide.learnGalaxyRomanConversion(input, cypher) shouldBe expected
    }
  }

  "#learnLearnMetalCost" should {
    "return the MetalCostBook collection with a new entry" in {
      val metalCostBook = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.LearnMetalCost("iron", "pish pish", 3910d)
      val expected = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)

      GalaxyGuide.learnMetalCost(input, metalCostBook, cypher) shouldBe expected
    }

    "override exchange rate whit the newest version" in {
      val metalCostBook = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.LearnMetalCost("gold", "pish pish", 3910d)
      val expected = Map("silver" → 17d, "gold" → 195.5)

      GalaxyGuide.learnMetalCost(input, metalCostBook, cypher) shouldBe expected
    }

    "return the cypher untouched if the number is invalid" in {
      val metalCostBook = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.LearnMetalCost("gold", "pish glob tegj", 3910d)
      val expected = Map("silver" → 17d, "gold" → 14450d)

      GalaxyGuide.learnMetalCost(input, metalCostBook, cypher) shouldBe expected
    }
  }

  "#answerHowMuch" should {
    "return the correct answer" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowMuch("pish tegj glob glob")
      val expected = "pish tegj glob glob is 42"

      GalaxyGuide.answerHowMuch(input, cypher) shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowMuch("tegj pish tegj glob glob")
      val expected = "Sorry, tegj pish tegj glob glob is not a number"

      GalaxyGuide.answerHowMuch(input, cypher) shouldBe expected
    }
  }

  "#answerHowManyCredits" should {
    "return the correct answer" in {
      val metalCostBook = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyCredits("glob prok", "silver")
      val expected = "glob prok Silver is 68 Credits"

      GalaxyGuide.answerHowManyCredits(input, metalCostBook, cypher) shouldBe expected
    }

    "return an error message if the Metal is invalid" in {
      val metalCostBook = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyCredits("glob prok", "Jade")
      val expected = "Sorry, can't convert glob prok Jade in Credits"

      GalaxyGuide.answerHowManyCredits(input, metalCostBook, cypher) shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val metalCostBook = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyCredits("glob prok tegj", "Silver")
      val expected = "Sorry, can't convert glob prok tegj Silver in Credits"

      GalaxyGuide.answerHowManyCredits(input, metalCostBook, cypher) shouldBe expected
    }
  }

  "#answerHowManyMetails" should {
    "return the correct answer" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyMetals("glob", "gold", "silver")
      val expected = "glob Gold is tegj pish pish pish prok Silver"

      GalaxyGuide.answerHowManyMetals(input, exchangeRate, cypher) shouldBe expected
    }

    "return the correct answer (zero in conversion)" in {
      val exchangeRate = Map("silver" → 1445d, "gold" → 17d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyMetals("glob", "gold", "silver")
      val expected = "glob Gold is no Silver"

      GalaxyGuide.answerHowManyMetals(input, exchangeRate, cypher) shouldBe expected
    }

    "return an error message if `metal to` is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyMetals("glob", "gold", "jade")
      val expected = "Sorry, can't convert glob Gold in Jade"

      GalaxyGuide.answerHowManyMetals(input, exchangeRate, cypher) shouldBe expected
    }

    "return an error message if `metal from` is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyMetals("glob", "jade", "silver")
      val expected = "Sorry, can't convert glob Jade in Silver"

      GalaxyGuide.answerHowManyMetals(input, exchangeRate, cypher) shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyMetals("glob prok tegj", "gold", "silver")
      val expected = "Sorry, can't convert glob prok tegj Gold in Silver"

      GalaxyGuide.answerHowManyMetals(input, exchangeRate, cypher) shouldBe expected
    }
  }
}
