package ai.faculty.plotly.writer

import org.json4s._
import org.json4s.JsonDSL._

import ai.faculty.plotly.{
  FigureOptions, LegendOptions, Margins,
  emptyLegendOptions, emptyMargins}

object FigureOptionsWriter {

  def toJson(options: FigureOptions): JObject = (
    ("title" -> options.title) ~
    ("legend" -> legendAsJson(options.legendOptions)) ~
    ("showlegend" -> options.showLegend) ~
    ("margin" -> marginsAsJson(options.margins)) ~
    ("width" -> options.width) ~
    ("height" -> options.height) ~
    ("paper_bgcolor" ->
        options.paperBackgroundColor.map { ColorWriter.toJson }) ~
    ("plot_bgcolor" ->
        options.plotBackgroundColor.map { ColorWriter.toJson })
  )

  private def legendAsJson(options: LegendOptions): Option[JObject] =
    options match {
      case emptyLegendOptions() => None
      case _ => Some(
        ("x" -> options.x) ~
        ("y" -> options.y) ~
        ("xanchor" -> options.xAnchor.map { _.toString }) ~
        ("yanchor" -> options.yAnchor.map { _.toString }) ~
        ("bordercolor" -> options.borderColor.map { ColorWriter.toJson }) ~
        ("bgcolor" -> options.backgroundColor.map { ColorWriter.toJson }) ~
        ("font" -> FontWriter.toJson(options.font)) ~
        ("borderwidth" -> options.borderWidth)
      )
    }

  private def marginsAsJson(margins: Margins): Option[JObject] =
    margins match {
      case emptyMargins() => None
      case _ => Some(
        ("l" -> margins.left) ~
        ("r" -> margins.right) ~
        ("b" -> margins.bottom) ~
        ("t" -> margins.top)
      )
    }
}
