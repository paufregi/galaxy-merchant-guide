package pellis

import org.scalatest.{Matchers, WordSpec}

import pellis.converter.impl.BasicNumberConversionProvider
import pellis.core.GalaxyGuide
import pellis.core.impl.{BasicAnswerer, BasicCommandReader, BasicGalaxyGuide, BasicLearner}

class GalaxyGuideTest extends WordSpec with Matchers {

  val galaxyGuide: GalaxyGuide = new BasicGalaxyGuide(BasicCommandReader) with BasicLearner with BasicAnswerer with BasicNumberConversionProvider

  "#compute" should {
    "analyse the input and answer accordingly" in {
      val input = List(
        "glob is I",
        "prok is V",
        "pish is X",
        "tegj is L",
        "hnga is C",
        "mpor is D",
        "atre is M",
        "glob glob Silver is 34 Credits",
        "glob prok Gold is 57800 Credits",
        "pish pish Iron is 3910 Credits",
        "how much is pish tegj glob glob ?",
        "how many Credits is glob prok Silver ?",
        "how many Credits is glob prok Gold ?",
        "how many Credits is glob prok Iron ?",
        "how many Silver is glob Gold ?",
        "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?")

      val expected = List(
        "pish tegj glob glob is 42",
        "glob prok Silver is 68 Credit(s)",
        "glob prok Gold is 57800 Credit(s)",
        "glob prok Iron is 782 Credit(s)",
        "glob Gold is mpor hnga hnga hnga tegj Silver",
        "I have no idea what you are talking about"
      )

      val result = galaxyGuide.compute(input)

      result shouldBe expected
    }
  }
}
