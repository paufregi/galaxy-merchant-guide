package pellis

object Message {

  object error {
    def generic: String = "I have no idea what you are talking about"

    def number(number: String): String = s"Sorry, $number is not a number"

    def exchange(number: String, from: String, to: String): String =
      s"Sorry, can't convert $number ${from.capitalize} in ${to.capitalize}"
  }

  object answer {
    def howMuch(galaxyNumber: String, number: Double) = f"$galaxyNumber is $number%.0f"

    def howManyCredits(galaxyNumber: String, from: String, number: Double) =
      f"$galaxyNumber ${from.capitalize} is $number%.0f Credits"

    def howManyMetals(galaxyNumber: String, from: String, number: String, to: String) =
      f"$galaxyNumber ${from.capitalize} is $number ${to.capitalize}"
  }

}