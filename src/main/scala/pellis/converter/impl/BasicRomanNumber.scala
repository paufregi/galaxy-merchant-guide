package pellis.converter.impl

import pellis.converter.RomanNumber
import pellis.model.{IntToRomanCypher, RomanToIntCypher}

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class BasicRomanNumber extends RomanNumber{

  private val romanNumberRegEx = """^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"""

  private lazy val romanCypher: RomanToIntCypher = List(
    "M" → 1000,
    "CM" → 900,
    "D" → 500,
    "CD" → 400,
    "C" → 100,
    "XC" → 90,
    "L" → 50,
    "XL" → 40,
    "X" → 10,
    "IX" → 9,
    "V" → 5,
    "IV" → 4,
    "I" → 1)

  private lazy val intCypher: IntToRomanCypher = romanCypher.map(_ swap)

  override def validate(romanNumber: String): Boolean = romanNumber.matches(romanNumberRegEx)

  override def fromInt(number: Int): String = number match {
    case nonPositive if nonPositive <= 0 =>
      throw new NumberFormatException(s"Is not possible convert $number in roman number")
    case positive => Try(fromInt(positive, "", intCypher)) match {
      case Success(romanNumber) => romanNumber
      case Failure(_) => throw new NumberFormatException(s"Is not possible convert $number in roman number")
    }
  }

  private def fromInt(arabicNumber: Int, romanNumber: String, cypher: IntToRomanCypher): String = {
    if (cypher.isEmpty) {
      if (arabicNumber == 0) return romanNumber

      throw new NumberFormatException(s"$romanNumber is not a valid roman number")
    }

    val repetition = arabicNumber / cypher.head._1
    fromInt(
      arabicNumber - (cypher.head._1 * repetition),
      s"$romanNumber${cypher.head._2 * repetition}",
      cypher.tail)
  }

  override def toInt(romanNumber: String): Int = {
    if (validate(romanNumber))
      toInt(0, romanNumber, romanCypher)
    else
      throw new NumberFormatException(s"$romanNumber is not a valid roman number")
  }

  private def toInt(arabicNumber: Int, romanNumber: String, cypher: RomanToIntCypher): Int = {
    if (romanNumber.isEmpty) return arabicNumber
    if (romanNumber.startsWith(cypher.head._1))
      toInt(arabicNumber + cypher.head._2, romanNumber.substring(cypher.head._1.length), cypher)
    else
      toInt(arabicNumber, romanNumber, cypher.tail)
  }
}
