package pellis

import org.scalatest.{Matchers, WordSpec}

class RomanNumberTest extends WordSpec with Matchers {

  "#toInt" should {
    "convert `MMMDCCCLXXXVIII` into 3888" in {
      val input = "MMMDCCCLXXXVIII"

      RomanNumber.toInt(input) shouldBe 3888
    }

    "convert `CMXCIX` into 999" in {
      val input = "CMXCIX"

      RomanNumber.toInt(input) shouldBe 999
    }

    "convert `CDXLIV` into 444" in {
      val input = "CDXLIV"

      RomanNumber.toInt(input) shouldBe 444
    }

    "throw a NumberFormatException" in {
      val input = "MCMDCDCXCLXLXIXVIVI"

      assertThrows[NumberFormatException] {
        RomanNumber.toInt(input)
      }
    }
  }

  "#fromInt" should {
    "convert 3888 into `MMMDCCCLXXXVIII` " in {
      val input = 3888

      RomanNumber.fromInt(input) shouldBe "MMMDCCCLXXXVIII"
    }

    "convert 999 into `CMXCIX`" in {
      val input = 999

      RomanNumber.fromInt(input) shouldBe "CMXCIX"
    }

    "convert 444 into `CDXLIV`" in {
      val input = 444

      RomanNumber.fromInt(input) shouldBe "CDXLIV"
    }

    "throw a NumberFormatException" in {
      val input = 0

      assertThrows[NumberFormatException] {
        RomanNumber.fromInt(input)
      }
    }
  }
}
