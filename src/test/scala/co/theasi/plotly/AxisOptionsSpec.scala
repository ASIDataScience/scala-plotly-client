package co.theasi.plotly

import org.scalatest._

class AxisOptionsSpec extends FlatSpec with Matchers {
  "A AxisOptions" should "allow setting the axis type from string" in {
    val options = AxisOptions().axisType("log")
    options.axisType shouldEqual Some(AxisType.Log)
  }
}
