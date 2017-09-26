package pellis

import pellis.converter.impl.BasicNumberConversionProvider
import pellis.core.impl.{BasicAnswerer, BasicCommandReader, BasicGalaxyGuide, BasicLearner}
import pellis.utils.Parameter

import scala.io.Source
import scala.util.Try

object Main extends App {

  private val reader = BasicCommandReader

  private val galaxyGuide = new BasicGalaxyGuide(reader) with BasicLearner with BasicAnswerer with BasicNumberConversionProvider

  for (
    params ← Parameter.parse(args)
  ) elaborate(params)

  private def elaborate(param: Parameter): Unit = {

    val result = Try(readFile(param.file)).map(input ⇒ galaxyGuide.compute(input))

    result.getOrElse(List("ERROR: Impossible read the input")).foreach(println)
  }

  private def readFile(filename: String): List[String] = Source.fromFile(filename).getLines().toList
}
