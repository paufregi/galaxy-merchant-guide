package pellis.converter.impl

import pellis.converter.{GalaxyNumber, NumberConverterProvider, RomanNumber}

trait BasicNumberConversionProvider extends NumberConverterProvider{

  override protected val romanConverter: RomanNumber = new BasicRomanNumber
  override protected val galaxyConverter: GalaxyNumber = new BasicGalaxyNumber(romanConverter)

}