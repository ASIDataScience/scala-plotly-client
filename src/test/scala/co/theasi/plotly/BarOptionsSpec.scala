package co.theasi.plotly

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BarOptionsSpec extends AnyFlatSpec with Matchers {
  "BarOptions" should "support setting marker options via updater" in {
    val testColor = Color.rgba(1, 2, 3, 0.2)
    val expectedOptions = MarkerOptions().color(testColor)
    val barOptions = BarOptions().updatedMarker(_.color(testColor))
    barOptions.marker shouldEqual expectedOptions
  }
}
