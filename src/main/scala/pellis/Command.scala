package pellis

sealed abstract class Command

object Command {

  case object Unknown extends Command

  case object Empty extends Command

  case class LearnGalaxyRomanConversion(galaxySymbol: String, romanSymbol: String) extends Command

  case class LearnMetalCost(metal: String, quantity: String, value: Double) extends Command

  case class QuestionHowMuch(galaxyNumber: String) extends Command

  case class QuestionHowManyCredits(galaxyNumber: String, metal: String) extends Command

  case class QuestionHowManyMetals(galaxyNumber: String, metalFrom: String, metalTo: String) extends Command
}
