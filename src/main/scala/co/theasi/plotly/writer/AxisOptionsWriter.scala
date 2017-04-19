package co.theasi.plotly.writer

import org.json4s._
import org.json4s.JsonDSL._

import co.theasi.plotly.{AxisOptions, AxisType}

object AxisOptionsWriter {
  def toJson(options: AxisOptions): JObject = (
    ("title" -> options.title) ~
    ("ticklen" -> options.tickLength) ~
    ("zeroline" -> options.zeroLine) ~
    ("gridwidth" -> options.gridWidth) ~
    ("showgrid" -> options.grid) ~
    ("showline" -> options.line) ~
    ("linecolor" -> options.lineColor.map(ColorWriter.toJson _)) ~
    ("titlefont" -> FontWriter.toJson(options.titleFont)) ~
    ("tickfont" -> FontWriter.toJson(options.tickFont)) ~
    ("autotick" -> options.autoTick) ~
    ("dtick" -> options.tickSpacing) ~
    ("type" -> axisTypeToJson(options.axisType)) ~

    ("tickcolor" -> options.tickColor.map(ColorWriter.toJson _)) ~
    ("showticklabels" -> options.tickLabels)
  )

  private def axisTypeToJson(axisTypeMaybe: Option[AxisType.Value]): JValue = {
    axisTypeMaybe match {
      case Some(axisType) => JString(axisType.toString)
      case None => JNothing
    }
  }
}
