package pellis

import scala.io.Source
import scala.util.{Failure, Success, Try}

object Main extends App {

  Parameter.parse(args) match {
    case Some(param) => executeConversion(param)
    case _ => Unit
  }

  private def executeConversion(param: Parameter): Unit = {
    val output = Try(readFile(param.file)) match {
      case Success(input) => GalaxyGuide.compute(input)
      case Failure(_) => List("ERROR: Impossible read the input")
    }
    output.foreach(println)
  }

  private def readFile(filename: String): List[String] = {
    Source.fromFile(filename).getLines().toList
  }
}
