package pellis.utils

import org.scalatest.{Matchers, WordSpec}

class ParameterTest extends WordSpec with Matchers {

  "#parse" should {
    "should return Some(config) for a valid input" in {
      val fileName = "input.txt"
      val input = Seq(fileName)
      val expectedConf = Parameter(fileName)

      val result = Parameter.parse(input)

      result shouldBe Some(expectedConf)
    }

    "should return None otherwise" in {
      val input = Seq.empty

      val result = Parameter.parse(input)

      result shouldBe None
    }
  }

}