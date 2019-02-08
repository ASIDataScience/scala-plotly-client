package ai.faculty.plotly.writer

import ai.faculty.plotly.Color

object ColorWriter {
  def toJson(color: Color): String =
    s"rgba(${color.r}, ${color.g}, ${color.b}, ${color.a})"
}
