package pellis.converter

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}
import pellis.converter.impl.BasicGalaxyNumber
import pellis.model.GalaxyCypher

class BasicGalaxyNumberTest extends WordSpec with Matchers with MockFactory {

  val cipher: GalaxyCypher = Map(
    "glob" → "I",
    "prok" → "V",
    "pish" → "X",
    "tegj" → "L"
  )

  val fakeRomanConverter: RomanNumber =  mock[RomanNumber]
  val converter: GalaxyNumber = new BasicGalaxyNumber(fakeRomanConverter)

  "#toInt" should {
    "convert `tegj pish pish pish prok glob glob glob` into 88" in {
      val input = "tegj pish pish pish prok glob glob glob"

      (fakeRomanConverter.toInt _).expects("LXXXVIII").returning(88)

      converter.toInt(input, cipher) shouldBe 88
    }

    "throw a NumberFormatException for not valid input" in {
      val input = "tegj wrong glob"

      assertThrows[NumberFormatException] {
        converter.toInt(input, cipher)
      }
    }

    "throw a NumberFormatException if the input doesn't represent a valid roman number" in {
      val input = "tegj tegj tegj tegj pish glob"

      (fakeRomanConverter.toInt _).expects(*).throws(new NumberFormatException)

      assertThrows[NumberFormatException] {
        converter.toInt(input, cipher)
      }
    }
  }

  "#fromInt" should {
    "convert 88 into `tegj pish pish pish prok glob glob glob`" in {
      val input = 88

      (fakeRomanConverter.fromInt _).expects(88).returning("LXXXVIII")
      (fakeRomanConverter.validate _).expects("LXXXVIII").returning(true)

      converter.fromInt(input, cipher) shouldBe "tegj pish pish pish prok glob glob glob"
    }

    "throw a NumberFormatException for not valid input" in {
      val input = -1

      (fakeRomanConverter.fromInt _).expects(-1).throwing(new NumberFormatException)

      assertThrows[NumberFormatException] {
        converter.fromInt(input, cipher)
      }
    }
  }

  "#toRoman" should {
    "convert `tegj pish pish pish prok glob glob glob` into `LXXXVIII`" in {
      val input = "tegj pish pish pish prok glob glob glob"

      converter.toRoman(input, cipher) shouldBe "LXXXVIII"
    }

    "throw a NumberFormatException for not valid input" in {
      val input = "tegj wrong glob"

      assertThrows[NumberFormatException] {
        converter.toRoman(input, cipher)
      }
    }
  }

  "#fromRoman" should {
    "convert `LXXXVIII` into `tegj pish pish pish prok glob glob glob`" in {
      val input = "LXXXVIII"

      (fakeRomanConverter.validate _).expects("LXXXVIII").returning(true)

      converter.fromRoman(input, cipher) shouldBe "tegj pish pish pish prok glob glob glob"
    }

    "throw a NumberFormatException if the conversion is not possible" in {
      val input = "C"

      (fakeRomanConverter.validate _).expects("C").returning(true)

      assertThrows[NumberFormatException] {
        converter.fromRoman(input, cipher)
      }
    }

    "throw a NumberFormatException if the input doesn't represent a valid roman number" in {
      val input = "MCMDCDCXCLXLXIXVIVI"

      (fakeRomanConverter.validate _).expects("MCMDCDCXCLXLXIXVIVI").returning(false)

      assertThrows[NumberFormatException] {
        converter.fromRoman(input, cipher)
      }
    }
  }
}
