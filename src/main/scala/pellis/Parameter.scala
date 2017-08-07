package pellis

import scopt.OptionParser

case class Parameter(file: String)

object Parameter {
  private val parser = new OptionParser[Parameter]("java -jar galaxy-merchant-guide.jar") {
    head("Merchant's Guide to the Galaxy")

    arg[String]("<file>")
      .required()
      .action( (file, config) => config.copy(file) )
      .text("input file")
  }

  def parse(args: Seq[String]): Option[Parameter] = parser.parse(args, Parameter(null))
}
