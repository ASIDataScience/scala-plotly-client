package co.theasi.plotly.writer

import co.theasi.plotly.{BarOptions, PlotType, ScatterOptions, SurfaceOptions}
import org.json4s.JsonDSL._
import org.json4s._

object SeriesWriter {
  def toJson(s: SeriesWriteInfo): JValue = {
    s.series.plotType match {
      case PlotType.SCATTER => scatterToJson(s)
      case PlotType.SCATTER3D => scatter3DToJson(s)
      case PlotType.BAR => barToJson(s)
      case PlotType.BOX => boxToJson(s)
      case PlotType.SURFACE => surfaceZToJson(s)
      case PlotType.SURFACEXYZ => surfaceXYZToJson(s)
      case PlotType.SCATTERMAPBOX => scatterMapboxToJson(s)
    }
  }

  private def scatterToJson(info: SeriesWriteInfo): JValue = {
    val List(xsrc, ysrc) = info.srcs

    ("xsrc" -> xsrc) ~
      ("ysrc" -> ysrc) ~
      axisToJson(info.axisIndex) ~
      OptionsWriter.scatterOptionsToJson(info.series.options.asInstanceOf[ScatterOptions])
  }

  private def scatterMapboxToJson(info: SeriesWriteInfo): JValue = {
    val List(xsrc, ysrc) = info.srcs

    ("lonsrc" -> xsrc) ~
      ("latsrc" -> ysrc) ~
      ("type" -> "scattermapbox") ~
      OptionsWriter.scatterOptionsToJson(info.series.options.asInstanceOf[ScatterOptions])
  }

  private def scatter3DToJson(info: SeriesWriteInfo): JValue = {
    val List(xsrc, ysrc, zsrc) = info.srcs

    ("xsrc" -> xsrc) ~
      ("ysrc" -> ysrc) ~
      ("zsrc" -> zsrc) ~
      axisToJson3D(info.axisIndex) ~
      ("type" -> "scatter3d") ~
      OptionsWriter.scatterOptionsToJson(info.series.options.asInstanceOf[ScatterOptions])
  }

  private def barToJson(info: SeriesWriteInfo)
  : JValue = {
    val List(xsrc, ysrc) = info.srcs
    ("xsrc" -> xsrc) ~
      ("ysrc" -> ysrc) ~
      axisToJson(info.axisIndex) ~
      ("type" -> "bar") ~
      OptionsWriter.barOptionsToJson(info.series.options.asInstanceOf[BarOptions])
  }

  private def boxToJson(info: SeriesWriteInfo)
  : JValue = {
    val List(xsrc) = info.srcs
    ("ysrc" -> xsrc) ~ axisToJson(info.axisIndex) ~ ("type" -> "box")
  }

  private def surfaceZToJson(info: SeriesWriteInfo)
  : JValue = {
    val List(zsrc) = info.srcs
    ("zsrc" -> zsrc) ~
      surfaceToJsonHelper(info.axisIndex, info.series.options.asInstanceOf[SurfaceOptions])
  }

  private def surfaceXYZToJson(info: SeriesWriteInfo): JValue = {
    val List(xsrc, ysrc, zsrc) = info.srcs
    ("xsrc" -> xsrc) ~
      ("ysrc" -> ysrc) ~
      ("zsrc" -> zsrc) ~
      surfaceToJsonHelper(info.axisIndex, info.series.options.asInstanceOf[SurfaceOptions])
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

  private def axisToJson3D(axisIndex: Int): JObject =
    axisIndex match {
      case 1 => ("xaxis" -> "x") ~ ("yaxis" -> "y") ~ ("zaxis" -> "z")
      case i => ("xaxis" -> s"x$i") ~ ("yaxis" -> s"y$i") ~ ("zaxis" -> s"z$i")
    }

  private def sceneToJson(plotIndex: Int): JObject =
    "scene" -> ("scene" + stringifyPlotIndex(plotIndex))

  private def stringifyPlotIndex(plotIndex: Int): String =
    plotIndex match {
      case 1 => ""
      case i => i.toString
    }
}
