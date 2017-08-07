package pellis

import scala.language.implicitConversions

object GalaxyNumber {
  def toInt(galaxyNumber: String, cypher: GalaxyCypher): Int = RomanNumber.toInt(toRoman(galaxyNumber, cypher))

  def toRoman(galaxyNumber: String, cypher: GalaxyCypher): String = {
    galaxyNumber.split(" ").map {
      str =>
        cypher.get(str) match {
          case Some(x) => x
          case None => throw new NumberFormatException(s"$galaxyNumber is not a valid number")
        }
    }.mkString
  }
}
