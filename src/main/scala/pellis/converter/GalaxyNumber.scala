package pellis.converter

import pellis.model.GalaxyCypher

import scala.language.{implicitConversions, postfixOps}

trait GalaxyNumber {

  def toInt(galaxyNumber: String, cypher: GalaxyCypher): Int

  def fromInt(number: Int, cypher: GalaxyCypher): String

  def toRoman(galaxyNumber: String, cypher: GalaxyCypher): String

  def fromRoman(romanNumber: String, cypher: GalaxyCypher): String
}
