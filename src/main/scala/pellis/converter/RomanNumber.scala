package pellis.converter

trait RomanNumber {

  def validate(romanNumber: String): Boolean

  def fromInt(number: Int): String

  def toInt(romanNumber: String): Int
}
