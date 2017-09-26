package pellis.converter.impl

import pellis.converter.{GalaxyNumber, RomanNumber}
import pellis.model.GalaxyCypher

import scala.language.{implicitConversions, postfixOps}

class BasicGalaxyNumber(romanConverter: RomanNumber) extends GalaxyNumber {

  private def errorMessage(number: String): String = s"$number is not a valid number"

  override def toInt(galaxyNumber: String, cypher: GalaxyCypher): Int = romanConverter.toInt(toRoman(galaxyNumber, cypher))

  override def fromInt(number: Int, cypher: GalaxyCypher): String = fromRoman(romanConverter.fromInt(number), cypher)

  override def toRoman(galaxyNumber: String, cypher: GalaxyCypher): String = {
    galaxyNumber.split(" ").map {
      str =>
        cypher.get(str) match {
          case Some(x) => x
          case None => throw new NumberFormatException(errorMessage(galaxyNumber))
        }
    }.mkString
  }

  override def fromRoman(romanNumber: String, cypher: GalaxyCypher): String = {
    if(!romanConverter.validate(romanNumber)) throw new NumberFormatException(errorMessage(romanNumber))
    val reverseCypher = cypher.map(_ swap)

    romanNumber.map {
      char => reverseCypher.get(char.toString) match {
        case Some(x) => x
        case None => throw new NumberFormatException(errorMessage(romanNumber))
      }
    }.mkString(" ")
  }
}
