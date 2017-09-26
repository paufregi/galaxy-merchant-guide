package pellis.model

sealed abstract class Command

object Command {

  case object Unknown extends Command

  case object Empty extends Command

  case class LearnGalaxyRomanConversion(galaxySymbol: String, romanSymbol: String) extends Command

  case class LearnMetalCost(quantity: String, metal: String, value: Double) extends Command

  case class QuestionHowMuch(galaxyNumber: String) extends Command

  case class QuestionHowManyCredits(quantity: String, metal: String) extends Command

  case class QuestionHowManyMetals(quantity: String, metalFrom: String, metalTo: String) extends Command
}
