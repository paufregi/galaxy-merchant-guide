package pellis.core

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}
import pellis.core.impl.BasicAnswerer
import pellis.model.{Answer, Command}
import pellis.testUtils.MockNumberConverterProvider

class BasicAnswererTest extends WordSpec with Matchers with MockFactory{

  private val answerer = new BasicAnswerer() with MockNumberConverterProvider with MockFactory

  "#answerHowMuch" should {
    "return the correct answer" in {
      val cypher = Map.empty[String, String]

      (answerer.fakeGalaxyConverter.toInt _).expects("pish tegj glob glob", cypher).returning(42)

      val input = Command.QuestionHowMuch("pish tegj glob glob")
      val expected = Answer.HowMuch("pish tegj glob glob", 42d)

      val result = answerer.answerHowMuch(input, cypher)

      result shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val cypher = Map.empty[String, String]

      (answerer.fakeGalaxyConverter.toInt _).expects("tegj pish tegj glob glob", cypher).throwing(new NumberFormatException)

      val input = Command.QuestionHowMuch("tegj pish tegj glob glob")
      val expected = Answer.NumberError("tegj pish tegj glob glob")

      val result =  answerer.answerHowMuch(input, cypher)

      result shouldBe expected
    }
  }

  "#answerHowManyCredits" should {
    "return the correct answer" in {
      val cypher = Map.empty[String, String]
      val metalCostBook = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)

      (answerer.fakeGalaxyConverter.toInt _).expects("glob prok", cypher).returning(4)

      val input = Command.QuestionHowManyCredits("glob prok", "silver")
      val expected = Answer.HowManyCredits("glob prok", "silver", 20d)

      val result = answerer.answerHowManyCredits(input, metalCostBook, cypher)

      result shouldBe expected
    }

    "return an error message if the Metal is invalid" in {
      val metalCostBook = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyCredits("glob prok", "Jade")
      val expected = Answer.ExchangeError("glob prok", "jade", "credits")

      val result = answerer.answerHowManyCredits(input, metalCostBook, cypher)

      result shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val metalCostBook = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map("glob" → "I", "prok" → "V", "pish" → "X", "tegj" → "L")

      val input = Command.QuestionHowManyCredits("glob prok tegj", "Silver")
      val expected =  Answer.ExchangeError("glob prok tegj", "silver", "credits")

      val result = answerer.answerHowManyCredits(input, metalCostBook, cypher)

      result shouldBe expected
    }
  }

  "#answerHowManyMetails" should {
    "return the correct answer" in {
      val exchangeRate = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map.empty[String, String]

      (answerer.fakeGalaxyConverter.toInt _).expects("glob", cypher).returning(1)
      (answerer.fakeGalaxyConverter.fromInt _).expects(2, cypher).returning("glob glob")

      val input = Command.QuestionHowManyMetals("glob", "gold", "silver")
      val expected = Answer.HowManyMetals("glob", "gold", "glob glob", "silver")

      val result = answerer.answerHowManyMetals(input, exchangeRate, cypher)

      result shouldBe expected
    }

    "return the correct answer (zero in conversion)" in {
      val exchangeRate = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map.empty[String, String]

      (answerer.fakeGalaxyConverter.toInt _).expects("glob", cypher).returning(1)

      val input = Command.QuestionHowManyMetals("glob", "silver", "gold")
      val expected = Answer.HowManyMetals("glob", "gold", "no", "silver")

      val result = answerer.answerHowManyMetals(input, exchangeRate, cypher)

      result shouldBe expected
    }

    "return an error message if `metal to` is invalid" in {
      val exchangeRate = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map.empty[String, String]

      val input = Command.QuestionHowManyMetals("glob", "gold", "jade")
      val expected = Answer.ExchangeError("glob", "gold", "jade")

      val result = answerer.answerHowManyMetals(input, exchangeRate, cypher)

      result shouldBe expected
    }

    "return an error message if `metal from` is invalid" in {
      val exchangeRate = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map.empty[String, String]

      val input = Command.QuestionHowManyMetals("glob", "jade", "silver")
      val expected = Answer.ExchangeError("glob", "jade", "silver")

      val result = answerer.answerHowManyMetals(input, exchangeRate, cypher)

      result shouldBe expected
    }

    "return an error message if the number is invalid" in {
      val exchangeRate = Map("iron" → 1d, "silver" → 5d, "gold" → 10d)
      val cypher = Map.empty[String, String]

      (answerer.fakeGalaxyConverter.toInt _).expects("glob prok tegj", cypher).throwing(new NumberFormatException)

      val input = Command.QuestionHowManyMetals("glob prok tegj", "gold", "silver")
      val expected =  Answer.ExchangeError("glob prok tegj", "gold", "silver")

      val result = answerer.answerHowManyMetals(input, exchangeRate, cypher)

      result shouldBe expected
    }
  }
}
