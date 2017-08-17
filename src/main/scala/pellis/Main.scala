package pellis

import scala.io.Source
import scala.util.Try

object Main extends App {

  for (
    params ← Parameter.parse(args)
  ) executeConversion(params)

  private def executeConversion(param: Parameter): Unit = {
    val result = Try(readFile(param.file)).map(input ⇒ GalaxyGuide.compute(input))

    result.getOrElse(List("ERROR: Impossible read the input")).foreach(println)
  }

  private def readFile(filename: String): List[String] = Source.fromFile(filename).getLines().toList
}
