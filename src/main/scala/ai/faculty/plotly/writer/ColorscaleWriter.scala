package ai.faculty.plotly.writer

import ai.faculty.plotly.{Colorscale, ColorscalePredef}

import org.json4s._

object ColorscaleWriter {
  def toJson(colorscale: Colorscale): JValue = {
    colorscale match {
      case ColorscalePredef(v) => JString(v)
    }
  }
}
