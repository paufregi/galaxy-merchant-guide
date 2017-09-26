package pellis.converter

import org.scalatest.{Matchers, WordSpec}
import pellis.converter.impl.BasicRomanNumber

class BasicRomanNumberTest extends WordSpec with Matchers {
  val converter: RomanNumber = new BasicRomanNumber

  "#toInt" should {
    "convert `MMMDCCCLXXXVIII` into 3888" in {
      val input = "MMMDCCCLXXXVIII"

      converter.toInt(input) shouldBe 3888
    }

    "convert `CMXCIX` into 999" in {
      val input = "CMXCIX"

      converter.toInt(input) shouldBe 999
    }

    "convert `CDXLIV` into 444" in {
      val input = "CDXLIV"

      converter.toInt(input) shouldBe 444
    }

    "throw a NumberFormatException" in {
      val input = "MCMDCDCXCLXLXIXVIVI"

      assertThrows[NumberFormatException] {
        converter.toInt(input)
      }
    }
  }

  "#fromInt" should {
    "convert 3888 into `MMMDCCCLXXXVIII` " in {
      val input = 3888

      converter.fromInt(input) shouldBe "MMMDCCCLXXXVIII"
    }

    "convert 999 into `CMXCIX`" in {
      val input = 999

      converter.fromInt(input) shouldBe "CMXCIX"
    }

    "convert 444 into `CDXLIV`" in {
      val input = 444

      converter.fromInt(input) shouldBe "CDXLIV"
    }

    "throw a NumberFormatException" in {
      val input = 0

      assertThrows[NumberFormatException] {
        converter.fromInt(input)
      }
    }
  }
}
