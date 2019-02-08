package ai.faculty.plotly

import org.scalatest.{Matchers, FlatSpec}

class BarOptionsSpec extends FlatSpec with Matchers {
  "BarOptions" should "support setting marker options via updater" in {
    val testColor = Color.rgba(1, 2, 3, 0.2)
    val expectedOptions = MarkerOptions().color(testColor)
    val barOptions = BarOptions().updatedMarker(_.color(testColor))
    barOptions.marker shouldEqual expectedOptions
  }
}
