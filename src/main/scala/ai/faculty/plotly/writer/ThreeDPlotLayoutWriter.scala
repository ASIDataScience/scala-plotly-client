package ai.faculty.plotly.writer

import org.json4s._
import org.json4s.JsonDSL._

import ai.faculty.plotly.{ViewPort, ThreeDPlot, ThreeDPlotOptions}

object ThreeDPlotLayoutWriter {

  def toJson(
    sceneIndex: Int,
    viewPort: ViewPort,
    plot: ThreeDPlot)
  : JObject = {
    val indexString = (if(sceneIndex == 1) "" else sceneIndex.toString)
    val label = "scene" + indexString
    val sceneDomain = sceneDomainToJson(viewPort)
    val options = optionsToJson(plot.options)
    label -> (sceneDomain ~ options)
  }

  private def sceneDomainToJson(viewPort: ViewPort): JObject = (
    "domain" ->
      ("x" -> pairToList(viewPort.xDomain)) ~
      ("y" -> pairToList(viewPort.yDomain))
  )

  private def pairToList(pair: (Double, Double)): List[Double] = {
    val (start, end) = pair
    List(start, end)
  }

  private def optionsToJson(options: ThreeDPlotOptions): JObject = (
    ("xaxis" -> AxisOptionsWriter.toJson(options.xAxisOptions)) ~
    ("yaxis" -> AxisOptionsWriter.toJson(options.yAxisOptions)) ~
    ("zaxis" -> AxisOptionsWriter.toJson(options.zAxisOptions))
  )

}
