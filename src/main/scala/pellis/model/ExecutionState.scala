package pellis.model

case class ExecutionState(output: List[String], costBook: MetalCostBook, cypher: GalaxyCypher){
  def addOutput(newEntry: String): ExecutionState = copy(output = output :+ newEntry.toString)

  def updateMetalCostBook(newCostBook: MetalCostBook): ExecutionState = copy(costBook = newCostBook)

  def updateCypher(newCypher: GalaxyCypher): ExecutionState = copy(cypher = newCypher)
}

object ExecutionState {
  def empty: ExecutionState = ExecutionState(List.empty, Map.empty, Map.empty)
}