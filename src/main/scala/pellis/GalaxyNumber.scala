package pellis

import scala.language.implicitConversions
import scala.language.postfixOps

object GalaxyNumber {
  def toInt(galaxyNumber: String, cypher: GalaxyCypher): Int = RomanNumber.toInt(toRoman(galaxyNumber, cypher))

  def fromInt(number: Int, cypher: GalaxyCypher): String = fromRoman(RomanNumber.fromInt(number), cypher)

  def toRoman(galaxyNumber: String, cypher: GalaxyCypher): String = {
    galaxyNumber.split(" ").map {
      str =>
        cypher.get(str) match {
          case Some(x) => x
          case None => throw new NumberFormatException(s"$galaxyNumber is not a valid number")
        }
    }.mkString
  }

  def fromRoman(romanNumber: String, cypher: GalaxyCypher): String = {
    if(!RomanNumber.validate(romanNumber)) throw new NumberFormatException(s"$romanNumber is not a valid number")

    val reverseCypher = cypher.map(_ swap)
    romanNumber.map {
      char => reverseCypher.get(char.toString) match {
        case Some(x) => x
        case None => throw new NumberFormatException(s"$romanNumber is not a valid number")
      }
    }.mkString(" ")
  }
}
