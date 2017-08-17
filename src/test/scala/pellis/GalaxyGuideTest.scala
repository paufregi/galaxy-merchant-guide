package pellis

import org.scalatest.{Matchers, WordSpec}

class GalaxyGuideTest extends WordSpec with Matchers {

  "#compute" should {
    "analyse the input and answer accordingly" in {
      val input = List(
        "glob is I",
        "prok is V",
        "pish is X",
        "tegj is L",
        "hnga is C",
        "mpor is D",
        "atre is M",
        "glob glob Silver is 34 Credits",
        "glob prok Gold is 57800 Credits",
        "pish pish Iron is 3910 Credits",
        "how much is pish tegj glob glob ?",
        "how many Credits is glob prok Silver ?",
        "how many Credits is glob prok Gold ?",
        "how many Credits is glob prok Iron ?",
        "how many Silver is glob Gold ?",
        "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?")

      val expected = List(
        "pish tegj glob glob is 42",
        "glob prok Silver is 68 Credits",
        "glob prok Gold is 57800 Credits",
        "glob prok Iron is 782 Credits",
        "glob Gold is mpor hnga hnga hnga tegj Silver",
        "I have no idea what you are talking about"
      )

      GalaxyGuide.compute(input) shouldBe expected
    }

    "skip empty lines" in {
      val input = List(
        "glob is I",
        "prok is V",
        "pish is X",
        "",
        "tegj is L",
        "hnga is C",
        "mpor is D",
        "atre is M",
        "glob glob Silver is 34 Credits",
        "glob prok Gold is 57800 Credits",
        "pish pish Iron is 3910 Credits",
        " ",
        "how much is pish tegj glob glob ?",
        "how many Credits is glob prok Silver ?",
        "how many Credits is glob prok Gold ?",
        "\t",
        "how many Credits is glob prok Iron ?",
        "how many Silver is glob Gold ?",
        "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?")

      val expected = List(
        "pish tegj glob glob is 42",
        "glob prok Silver is 68 Credits",
        "glob prok Gold is 57800 Credits",
        "glob prok Iron is 782 Credits",
        "glob Gold is mpor hnga hnga hnga tegj Silver",
        "I have no idea what you are talking about"
      )

      GalaxyGuide.compute(input) shouldBe expected
    }
  }

  "#learnGalaxyConversion" should {
    "return the cypher with a new entry" in {
      val cypher = Map("glob" → "I", "prok" → "V")
      val input: String = "pish is X"

      val expected = Map("glob" → "I", "prok" → "V", "pish" → "X")

      GalaxyGuide.learnRoman(cypher, input) shouldBe expected
    }

    "override conversion with the newest version" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X")
      val input: String = "prok is I"

      val expected = Map("prok" → "I", "pish" → "X")

      GalaxyGuide.learnRoman(cypher, input) shouldBe expected
    }

    "return the cypher untouched if the input is invalid" in {
      val cypher = Map("glob" → "I", "prok" → "V")
      val input: String = "invalid input"

      val expected = Map("glob" → "I", "prok" → "V")

      GalaxyGuide.learnRoman(cypher, input) shouldBe expected
    }

    "return the cypher untouched if the roman number is invalid" in {
      val cypher = Map("glob" → "I", "prok" → "V")
      val input: String = "pish is A"

      val expected = Map("glob" → "I", "prok" → "V")

      GalaxyGuide.learnRoman(cypher, input) shouldBe expected
    }
  }

  "#learnExchangeRate" should {
    "return the exchange rate collection with a new entry" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "pish pish Iron is 3910 Credits"
      val expected = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)

      GalaxyGuide.learnExchangeRate(exchangeRate, cypher, input) shouldBe expected
    }

    "override exchange rate whit the newest version" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "pish pish Gold is 3910 Credits"
      val expected = Map("silver" → 17d, "gold" → 195.5)

      GalaxyGuide.learnExchangeRate(exchangeRate, cypher, input) shouldBe expected
    }

    "return the cypher untouched if the input is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "invalid input"
      val expected = Map("silver" → 17d, "gold" → 14450d)

      GalaxyGuide.learnExchangeRate(exchangeRate, cypher, input) shouldBe expected
    }

    "return the cypher untouched if the number is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "pish glob tegj Gold is 3910 Credits"
      val expected = Map("silver" → 17d, "gold" → 14450d)

      GalaxyGuide.learnExchangeRate(exchangeRate, cypher, input) shouldBe expected
    }
  }

  "#answerHowMuch" should {
    "return the correct answer" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how much is pish tegj glob glob ?"
      val expected = "pish tegj glob glob is 42"

      GalaxyGuide.answerHowMuch(cypher, input) shouldBe expected
    }

    "return an error message if the input is invalid" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "invalid input ?"
      val expected = "I have no idea what you are talking about"

      GalaxyGuide.answerHowMuch(cypher, input) shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how much is tegj pish tegj glob glob ?"
      val expected = "Sorry, tegj pish tegj glob glob is not a number"

      GalaxyGuide.answerHowMuch(cypher, input) shouldBe expected
    }
  }

  "#answerHowManyCredits" should {
    "return the correct answer" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Credits is glob prok Silver ?"
      val expected = "glob prok Silver is 68 Credits"

      GalaxyGuide.answerHowManyCredits(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if the input is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "invalid input ?"
      val expected = "I have no idea what you are talking about"

      GalaxyGuide.answerHowManyCredits(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if the currency is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Credits is glob prok Jade ?"
      val expected = "Sorry, can't convert glob prok Jade in Credits"

      GalaxyGuide.answerHowManyCredits(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 14450d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Credits is glob prok tegj Silver ?"
      val expected = "Sorry, can't convert glob prok tegj Silver in Credits"

      GalaxyGuide.answerHowManyCredits(exchangeRate, cypher, input) shouldBe expected
    }
  }

  "#answerHowManyMetails" should {
    "return the correct answer" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Silver is glob Gold ?"
      val expected = "glob Gold is tegj pish pish pish prok Silver"

      GalaxyGuide.answerHowManyMetals(exchangeRate, cypher, input) shouldBe expected
    }

    "return the correct answer (zero in conversion)" in {
      val exchangeRate = Map("silver" → 1445d, "gold" → 17d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Silver is glob Gold ?"
      val expected = "glob Gold is no Silver"

      GalaxyGuide.answerHowManyMetals(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if the input is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "invalid input ?"
      val expected = "I have no idea what you are talking about"

      GalaxyGuide.answerHowManyMetals(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if `metal to` is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Jade is glob Gold ?"
      val expected = "Sorry, can't convert glob Gold in Jade"

      GalaxyGuide.answerHowManyMetals(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if `metal from` is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Silver is glob Jade ?"
      val expected = "Sorry, can't convert glob Jade in Silver"

      GalaxyGuide.answerHowManyMetals(exchangeRate, cypher, input) shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val exchangeRate = Map("silver" → 17d, "gold" → 1445d, "iron" → 195.5)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = "how many Silver is glob prok tegj Gold ?"
      val expected = "Sorry, can't convert glob prok tegj Gold in Silver"

      GalaxyGuide.answerHowManyMetals(exchangeRate, cypher, input) shouldBe expected
    }
  }
}
