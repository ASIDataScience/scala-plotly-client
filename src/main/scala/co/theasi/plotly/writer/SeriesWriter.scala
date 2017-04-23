package co.theasi.plotly.writer

import org.json4s._
import org.json4s.JsonDSL._

import co.theasi.plotly.SurfaceOptions

object SeriesWriter {
  def toJson(seriesWriteInfo: SeriesWriteInfo)
  : JValue = {
    seriesWriteInfo match {
      case s: ScatterWriteInfo => scatterToJson(s)
      case s: BarWriteInfo => barToJson(s)
      case s: BoxWriteInfo => boxToJson(s)
      case s: SurfaceZWriteInfo => surfaceZToJson(s)
      case s: SurfaceXYZWriteInfo => surfaceXYZToJson(s)
    }
  }

  private def scatterToJson(info: ScatterWriteInfo)
  : JValue = {
    val List(xsrc, ysrc) = info.srcs

    ("xsrc" -> xsrc) ~
    ("ysrc" -> ysrc) ~
    axisToJson(info.axisIndex) ~
    OptionsWriter.scatterOptionsToJson(info.options)
  }

  private def barToJson(info: BarWriteInfo)
  : JValue = {
    val List(xsrc, ysrc) = info.srcs
    ("xsrc" -> xsrc) ~
    ("ysrc" -> ysrc) ~
    axisToJson(info.axisIndex) ~
    ("type" -> "bar") ~
    OptionsWriter.barOptionsToJson(info.options)
  }

  private def boxToJson(info: BoxWriteInfo)
  : JValue = {
    val List(xsrc) = info.srcs
    ("ysrc" -> xsrc) ~ axisToJson(info.axisIndex) ~ ("type" -> "box")
  }

  private def surfaceZToJson(info: SurfaceZWriteInfo)
  : JValue = {
    val List(zsrc) = info.srcs
    ("zsrc" -> zsrc) ~
    surfaceToJsonHelper(info.sceneIndex, info.options)
  }

  private def surfaceXYZToJson(info: SurfaceXYZWriteInfo): JValue = {
    val List(xsrc, ysrc, zsrc) = info.srcs
    ("xsrc" -> xsrc) ~
    ("ysrc" -> ysrc) ~
    ("zsrc" -> zsrc) ~
    surfaceToJsonHelper(info.sceneIndex, info.options)
  }

  private def surfaceToJsonHelper(plotIndex: Int, options: SurfaceOptions) = {
    ("type" -> "surface") ~
    sceneToJson(plotIndex) ~
    OptionsWriter.surfaceOptionsToJson(options)
  }

  private def axisToJson(axisIndex: Int): JObject =
    axisIndex match {
      case 1 => ("xaxis" -> "x") ~ ("yaxis" -> "y")
      case i => ("xaxis" -> s"x$i") ~ ("yaxis" -> s"y$i")
    }

  private def sceneToJson(plotIndex: Int): JObject =
    "scene" -> ("scene" + stringifyPlotIndex(plotIndex))

  private def stringifyPlotIndex(plotIndex: Int): String =
    plotIndex match {
      case 1 => ""
      case i => i.toString
    }
}
