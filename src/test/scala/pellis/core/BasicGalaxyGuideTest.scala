package pellis.core

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}
import pellis.core.impl.BasicGalaxyGuide
import pellis.model.Command._
import pellis.model.Answer
import pellis.testUtils.{MockAnswerer, MockLearner, MockNumberConverterProvider}

class BasicGalaxyGuideTest extends WordSpec with Matchers with MockFactory {

  private val fakeReader: CommandReader = mock[CommandReader]

  private val galaxyGuide = new BasicGalaxyGuide(fakeReader) with MockLearner with MockAnswerer with MockFactory

  "#compute" should {

    "skip empty command" in {
      val input = List("")

      val command = Empty

      (fakeReader.translate _).expects("").returning(command).once

      val result = galaxyGuide.compute(input)

      result shouldBe List.empty[String]
    }

    "produce a generic error message when the input is not understandable" in {
      val input = List("invalid input")

      (fakeReader.translate _).expects("invalid input").returning(Unknown).once

      val result = galaxyGuide.compute(input)

      result shouldBe List("I have no idea what you are talking about")
    }

    "delegate the `Learner` to store the number conversion and produce no output" in {
      val input = List("glob is I")

      val command = LearnGalaxyRomanConversion("glob", "I")
      val cypher = Map.empty[String, String]

      (fakeReader.translate _).expects("glob is I").returning(command).once
      galaxyGuide.fakeLearnGalaxyRomanConversion.expects(command, cypher).returning(cypher).once

      val result = galaxyGuide.compute(input)

      result shouldBe List.empty[String]
    }

    "delegate the `Learner` to store the metal cost and produce no output" in {
      val input = List("glob Silver is 1 Credits")

      val command = LearnMetalCost("glob", "silver", 1)
      val cypher = Map.empty[String, String]
      val costBook = Map.empty[String, Double]

      (fakeReader.translate _).expects("glob Silver is 1 Credits").returning(command).once
      galaxyGuide.fakeLearnMetalCost.expects(command, costBook, cypher).returning(costBook).once

      val result = galaxyGuide.compute(input)

      result shouldBe List.empty[String]
    }

    "delegate the `Answerer` to answer the `How much` question and produce the correct output" in {
      val input = List("how much is glob ?")

      val command = QuestionHowMuch("glob")
      val cypher = Map.empty[String, String]

      (fakeReader.translate _).expects("how much is glob ?").returning(command).once
      galaxyGuide.fakeAnswerHowMuch.expects(command, cypher).returning(Answer.HowMuch("glob", 1d)).once

      val result = galaxyGuide.compute(input)

      result shouldBe List("glob is 1")
    }

    "delegate the `Answerer` to answer the `How many credits` question and produce the correct output" in {
      val input = List("how many Credits is glob prok Silver ?")

      val command = QuestionHowManyCredits("glob", "silver")
      val cypher = Map.empty[String, String]
      val costBook = Map.empty[String, Double]

      (fakeReader.translate _).expects("how many Credits is glob prok Silver ?").returning(command).once
      galaxyGuide.fakeAnswerHowManyCredits.expects(command, costBook, cypher).returning(Answer.HowManyCredits("glob", "silver", 1d)).once

      val result = galaxyGuide.compute(input)

      result shouldBe List("glob Silver is 1 Credit(s)")
    }

    "delegate the `Answerer` to answer the `How many metals` question and produce the correct output" in {
      val input = List("how many Silver is glob Gold ?")

      val command = QuestionHowManyMetals("glob", "silver", "gold")
      val cypher = Map.empty[String, String]
      val costBook = Map.empty[String, Double]

      (fakeReader.translate _).expects("how many Silver is glob Gold ?").returning(command).once
      galaxyGuide.fakeAnswerHowManyMetals.expects(command, costBook, cypher).returning(Answer.HowManyMetals("glob", "silver", "glob", "gold")).once

      val result = galaxyGuide.compute(input)

      result shouldBe List("glob Silver is glob Gold")
    }
  }
}
