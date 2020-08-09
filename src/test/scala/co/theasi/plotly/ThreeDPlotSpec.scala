package co.theasi.plotly

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ThreeDPlotSpec extends AnyFlatSpec with Matchers {

  "A 3D plot" should "allow setting x-axis options" in {
    val p = ThreeDPlot().xAxisOptions(AxisOptions().title("hello"))
    p.options.xAxisOptions.title shouldEqual Some("hello")
  }

  it should "allow setting y-axis options" in {
    val p = ThreeDPlot().yAxisOptions(AxisOptions().title("hello"))
    p.options.yAxisOptions.title shouldEqual Some("hello")
  }

  it should "allow setting z-axis options" in {
    val p = ThreeDPlot().zAxisOptions(AxisOptions().title("hello"))
    p.options.zAxisOptions.title shouldEqual Some("hello")
  }

}
