package pellis

import org.scalatest.{Matchers, WordSpec}
import pellis.Command._

class CommandReaderTest extends WordSpec with Matchers {

  "#read" should {
    "return a `LearnGalaxyRomanConversion` instance" in {
      val input = "glob is I"

      val result = CommandReader.read(input)

      result shouldBe LearnGalaxyRomanConversion("glob", "I")
    }

    "return a `LearnMetalCost` instance" in {
      val input = "glob glob Silver is 34 Credits"

      val result = CommandReader.read(input)

      result shouldBe LearnMetalCost("silver", "glob glob", 34d)
    }

    "return a `QuestionHowMuch` instance" in {
      val input = "how much is pish tegj glob glob ?"

      val result = CommandReader.read(input)

      result shouldBe QuestionHowMuch("pish tegj glob glob")
    }

    "return a `QuestionHowManyCredits` instance" in {
      val input = "how many Credits is glob prok Silver ?"

      val result = CommandReader.read(input)

      result shouldBe QuestionHowManyCredits("glob prok", "silver")
    }

    "return a `QuestionHowManyMetals` instance" in {
      val input = "how many Silver is glob Gold ?"

      val result = CommandReader.read(input)

      result shouldBe QuestionHowManyMetals("glob", "gold", "silver")
    }

    "return a `Empty` object" in {
      val input = ""

      val result = CommandReader.read(input)

      result shouldBe Empty
    }

    "return a `UnknownCommand` instance" in {
      val input = "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"

      val result = CommandReader.read(input)

      result shouldBe Unknown
    }
  }

}