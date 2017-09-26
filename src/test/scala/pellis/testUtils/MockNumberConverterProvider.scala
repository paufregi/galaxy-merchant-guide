package pellis.testUtils

import org.scalamock.scalatest.MockFactory
import pellis.converter.{GalaxyNumber, NumberConverterProvider, RomanNumber}

trait MockNumberConverterProvider extends NumberConverterProvider {
  self: MockFactory ⇒

  val fakeRomanConverter: RomanNumber = mock[RomanNumber]("romanConverter")
  val fakeGalaxyConverter: GalaxyNumber = mock[GalaxyNumber]("galaxyConverter")

  override protected val romanConverter: RomanNumber = fakeRomanConverter
  override protected val galaxyConverter: GalaxyNumber = fakeGalaxyConverter
}
