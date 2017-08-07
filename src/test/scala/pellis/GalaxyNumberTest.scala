package pellis

import org.scalatest.{Matchers, WordSpec}

class GalaxyNumberTest extends WordSpec with Matchers {

  val cipher: GalaxyCypher = Map(
    "glob" -> "I",
    "prok" -> "V",
    "pish" -> "X",
    "tegj" -> "L"
  )

  "#toInt" should {
    "convert `tegj pish pish pish prok glob glob glob` into 88" in {
      val input = "tegj pish pish pish prok glob glob glob"

      GalaxyNumber.toInt(input, cipher) shouldBe 88
    }

    "throw a NumberFormatException for not valid input" in {
      val input = "tegj wrong glob"

      assertThrows[NumberFormatException] {
        GalaxyNumber.toInt(input, cipher)
      }
    }

    "throw a NumberFormatException if the input doesn't represent a valid roman number" in {
      val input = "tegj tegj tegj tegj pish glob"

      assertThrows[NumberFormatException] {
        GalaxyNumber.toInt(input, cipher)
      }
    }
  }

  "#toRoman" should {
    "convert `tegj pish pish pish prok glob glob glob` into `LXXXVIII`" in {
      val input = "tegj pish pish pish prok glob glob glob"

      GalaxyNumber.toRoman(input, cipher) shouldBe "LXXXVIII"
    }

    "throw a NumberFormatException for not valid input" in {
      val input = "tegj wrong glob"

      assertThrows[NumberFormatException] {
        GalaxyNumber.toInt(input, cipher)
      }
    }

    "throw a NumberFormatException if the input doesn't represent a valid roman number" in {
      val input = "tegj tegj tegj tegj pish glob"

      assertThrows[NumberFormatException] {
        GalaxyNumber.toInt(input, cipher)
      }
    }
  }
}
