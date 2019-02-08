package ai.faculty.plotly

import org.scalatest._

class CartesianPlotSpec extends FlatSpec with Matchers {

  "A CartesianPlot" should "allow setting x-axis options" in {
    val p = CartesianPlot().xAxisOptions(AxisOptions().title("hello"))
    p.options.xAxis.options.title shouldEqual Some("hello")
  }

  it should "allow adding y-axis options" in {
    val p = CartesianPlot().yAxisOptions(AxisOptions().title("hello"))
    p.options.yAxis.options.title shouldEqual Some("hello")
  }
}
