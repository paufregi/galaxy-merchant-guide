package pellis

object RomanNumber {

  private val romanNumberRegEx = """^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"""

  private val romanCypher: RomanCypher = List(
    "M" -> 1000,
    "CM" -> 900,
    "D" -> 500,
    "CD" -> 400,
    "C" -> 100,
    "XC" -> 90,
    "L" -> 50,
    "XL" -> 40,
    "X" -> 10,
    "IX" -> 9,
    "V" -> 5,
    "IV" -> 4,
    "I" -> 1)

  def validate(romanNumber: String): Boolean = romanNumber.matches(romanNumberRegEx)

  def toInt(romanNumber: String): Int = {
    if (validate(romanNumber))
      toInt(0, romanNumber, romanCypher)
    else
      throw new NumberFormatException(s"$romanNumber is not a valid roman number")
  }

  private def toInt(arabicNumber: Int, romanNumber: String, cipher: RomanCypher): Int = {
    if (romanNumber.isEmpty) return arabicNumber
    if (romanNumber.startsWith(cipher.head._1))
      toInt(arabicNumber + cipher.head._2, romanNumber.substring(cipher.head._1.length), cipher)
    else
      toInt(arabicNumber, romanNumber, cipher.tail)
  }
}
