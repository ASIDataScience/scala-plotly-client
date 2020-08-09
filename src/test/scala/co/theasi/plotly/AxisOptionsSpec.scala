package co.theasi.plotly

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AxisOptionsSpec extends AnyFlatSpec with Matchers {
  "A AxisOptions" should "allow setting the axis type from string" in {
    val options = AxisOptions().axisType("log")
    options.axisType shouldEqual Some(AxisType.Log)
  }
}
