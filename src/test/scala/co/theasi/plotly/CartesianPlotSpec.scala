package co.theasi.plotly

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CartesianPlotSpec extends AnyFlatSpec with Matchers {

  "A CartesianPlot" should "allow setting x-axis options" in {
    val p = CartesianPlot().xAxisOptions(AxisOptions().title("hello"))
    p.options.xAxis.options.title shouldEqual Some("hello")
  }

  it should "allow adding y-axis options" in {
    val p = CartesianPlot().yAxisOptions(AxisOptions().title("hello"))
    p.options.yAxis.options.title shouldEqual Some("hello")
  }
}
