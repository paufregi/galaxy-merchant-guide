package pellis.core

import pellis.model.Command

trait CommandReader {

  def translate(input: String): Command
}
