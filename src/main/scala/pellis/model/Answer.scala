package pellis.model

sealed abstract class Answer

object Answer {

  case class HowMuch(galaxyNumber: String, number: Double) extends Answer {
    override def toString: String = {
      f"$galaxyNumber is $number%.0f"
    }
  }

  case class HowManyCredits(quantity: String, metal: String, credits: Double) extends Answer {
    override def toString: String = {
      f"$quantity ${metal.capitalize} is $credits%.0f Credit(s)"
    }
  }

  case class HowManyMetals(quantityFrom: String, metalFrom: String, quantityTo: String, metalTo: String) extends Answer {
    override def toString: String = {
      f"$quantityFrom ${metalFrom.capitalize} is $quantityTo ${metalTo.capitalize}"
    }
  }

  case object GenericError extends Answer {
    override def toString: String = {
      "I have no idea what you are talking about"
    }
  }

  case class NumberError(number: String) extends Answer {
    override def toString: String = {
      s"Sorry, $number is not a number"
    }
  }

  case class ExchangeError(quantity: String, metalFrom: String, metalTo: String) extends Answer {
    override def toString: String = {
      s"Sorry, can't convert $quantity ${metalFrom.capitalize} in ${metalTo.capitalize}"
    }
  }
}