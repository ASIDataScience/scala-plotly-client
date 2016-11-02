package co.theasi.plotly.writer

import org.json4s._
import org.json4s.JsonDSL._

import co.theasi.plotly.{ViewPort, AxisOptions, CartesianPlot}

object CartesianPlotLayoutWriter {

  def toJson(
    axisIndex: Int,
    viewPort: ViewPort,
    plot: CartesianPlot)
  : JObject = {
    val xAxis = axisToJson(
      axisIndex, viewPort.xDomain, "xaxis", "y", plot.options.xAxis.options)
    val yAxis = axisToJson(
      axisIndex, viewPort.yDomain, "yaxis", "x", plot.options.yAxis.options)

    xAxis ~ yAxis
  }


  private def axisToJson(
    axisIndex: Int,
    domain: (Double, Double),
    radix: String,
    anchorRadix: String,
    options: AxisOptions)
  : JObject = {
    val indexString = (if(axisIndex == 1) "" else axisIndex.toString)
    val label = radix + indexString
    val (start, end) = domain
    val body = (
      ("domain" -> List(start, end)) ~
      ("anchor" -> (anchorRadix + indexString)) ~
      AxisOptionsWriter.toJson(options)
    )
    JObject(JField(label, body))
  }

}
