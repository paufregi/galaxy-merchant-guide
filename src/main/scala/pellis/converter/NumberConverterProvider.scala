package pellis.converter

trait NumberConverterProvider {

  protected val romanConverter: RomanNumber
  protected val galaxyConverter: GalaxyNumber
}
