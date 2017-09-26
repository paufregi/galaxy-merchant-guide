package pellis.core.impl

import pellis.core.{Answerer, CommandReader, GalaxyGuide, Learner}
import pellis.model.{Answer, Command, ExecutionState}

class BasicGalaxyGuide(reader: CommandReader) extends GalaxyGuide {
  this: Learner with Answerer ⇒

  override def compute(input: List[String]): List[String] = {
    val execution = input.foldLeft(ExecutionState.empty) {
      (execution, line) ⇒ {
        val command = reader.translate(line)
        execute(execution, command)
      }
    }
    execution.output
  }

  private def execute(state: ExecutionState, command: Command): ExecutionState = command match {
    case command: Command.LearnGalaxyRomanConversion ⇒
      val newCypher = learnGalaxyRomanConversion(command, state.cypher)
      state.updateCypher(newCypher)

    case command: Command.LearnMetalCost ⇒
      val newCostBook = learnMetalCost(command, state.costBook, state.cypher)
      state.updateMetalCostBook(newCostBook)

    case command: Command.QuestionHowMuch ⇒
      val answer = answerHowMuch(command, state.cypher)
      state.addOutput(answer.toString)

    case command: Command.QuestionHowManyCredits ⇒
      val answer = answerHowManyCredits(command, state.costBook, state.cypher)
      state.addOutput(answer.toString)

    case command: Command.QuestionHowManyMetals ⇒
      val answer = answerHowManyMetals(command, state.costBook, state.cypher)
      state.addOutput(answer.toString)

    case Command.Empty ⇒
      state

    case _ ⇒
      state.addOutput(Answer.GenericError.toString)
  }
}
