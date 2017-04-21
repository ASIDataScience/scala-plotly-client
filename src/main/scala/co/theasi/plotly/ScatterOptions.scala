package co.theasi.plotly

/** Options controlling how scatter and line plots are plotted. */
case class ScatterOptions(
  name: Option[String],
  mode: Seq[ScatterMode.Value],
  text: Option[TextValue],
  marker: MarkerOptions,
  line: LineOptions
) extends SeriesOptions {

  /** Set the name of the series */
  def name(newName: String): ScatterOptions =
    copy(name = Some(newName))

  /** Set the line mode.
    *
    * Possible arguments are `ScatterMode.Marker`, `ScatterMode.Line`
    * and `ScatterMode.Text`. These are additive:
    * `.mode(ScatterMode.Marker, ScatterMode.Line)` will show both
    * markers and a line between them.
    *
    * `ScatterMode.Text` indicates that the text corresponding to
    * a particular point should always be shown (by default, it is
    * shown when the user hovers over the point).
    */
  def mode(newMode: ScatterMode.Value, rest: ScatterMode.Value*)
  : ScatterOptions = mode(newMode +: rest)

  /** Set the line mode.
    *
    * Possible arguments are `ScatterMode.Marker`, `ScatterMode.Line`
    * and `ScatterMode.Text`. These are additive:
    * `.mode(List(ScatterMode.Marker, ScatterMode.Line))`
    * will show both markers and a line between them.
    *
    * `ScatterMode.Text` indicates that the text corresponding to
    * a particular point should always be shown (by default, it is
    * shown when the user hovers over the point).
    */
  def mode(newModes: Iterable[ScatterMode.Value]): ScatterOptions =
    copy(mode = newModes.toSeq)

  /** Set the text labels for points in this series.
    *
    * This sets the same label for every point.
    */
  def text(newText: String): ScatterOptions =
    copy(text = Some(StringText(newText)))

  /** Set the text labels for points in this series.
    *
    * This sets a different label for each point. The iterator
    * `newText` must be the same length as the data series.
    */
  def text[T: Writable](newText: Iterable[T]): ScatterOptions = {
    val textAsPType = newText.map { implicitly[Writable[T]].toPType }
    copy(text = Some(IterableText(textAsPType)))
  }

  /** Set the text labels for points in this series.
    *
    * This sets the labels from data that is already in Plotly.
    *
    * @param src String of format `fileId:columnUid`, where
    * `fileId` is the id of a grid in Plotly and column uid is
    * the id of a column in that grid.
    *
    * @example {{{
    * import co.theasi.plotly._
    * val gridFile = writer.GridFile.fromFileName("lowest-oecd-votes-cast-grid")
    *
    * val fileId = gridFile.fileId
    * val columnUid = gridFile.columnUids("y-0")
    *
    * val textSrc = s"$fileId:$columnUid"
    *
    * val xs = (1 to 10)
    * val ys = (1 to 10)
    * val p = CartesianPlot()
    *   .withScatter(xs, ys, ScatterOptions().textSrc(textSrc))
    *
    * draw(p, "text-src-example")
    * }}}
    */
  def textSrc(src: String): ScatterOptions =
    copy(text = Some(SrcText(src)))

  /** Set new [[MarkerOptions]] for this series.
    *
    * @see [[ScatterOptions.updatedMarker]] to update an
    *   existing set of marker options.
    */
  def marker(newMarker: MarkerOptions): ScatterOptions =
    copy(marker = newMarker)

  /** Update the [[MarkerOptions]] for this series.
    *
    * @param updater Function mapping the existing [[MarkerOptions]]
    *   to new [[MarkerOptions]].
    *
    * @example {{{
    * val xs = (1 to 10)
    * val ys = (1 to 10)
    *
    * val p = CartesianPlot()
    *   .withScatter(xs, ys, ScatterOptions()
    *     .updatedMarker(_.size(10).symbol("x")))
    * }}}
    */
  def updatedMarker(updater: MarkerOptions => MarkerOptions)
  : ScatterOptions = {
    val newMarker = updater(marker)
    marker(newMarker)
  }

  def line(newLine: LineOptions): ScatterOptions =
    copy(line = newLine)

  def updatedLine(updater: LineOptions => LineOptions)
  : ScatterOptions = {
    val newLine = updater(line)
    line(newLine)
  }

}

object ScatterOptions {
  def apply(): ScatterOptions = ScatterOptions(
    name = None,
    mode = Seq.empty,
    text = None,
    marker = MarkerOptions(),
    line = LineOptions()
  )
}

object ScatterMode extends Enumeration {
  val Marker = Value("markers")
  val Line = Value("lines")
  val Text = Value("text")
}

case class MarkerOptions(
  size: Option[Int],
  color: Option[Color],
  symbol: Option[String],
  lineWidth: Option[Int],
  lineColor: Option[Color]
) {

  def size(newSize: Int): MarkerOptions = copy(size = Some(newSize))
  def color(newColor: Color): MarkerOptions =
    copy(color = Some(newColor))
  def color(r: Int, g: Int, b: Int, a: Double): MarkerOptions =
    color(Color.rgba(r, g, b, a))
  def color(r: Int, g: Int, b: Int): MarkerOptions =
    color(r, g, b, 1.0)

  def symbol(newSymbol: String): MarkerOptions =
    copy(symbol = Some(newSymbol))

  def lineWidth(newLineWidth: Int): MarkerOptions =
    copy(lineWidth = Some(newLineWidth))
  def lineColor(newLineColor: Color): MarkerOptions =
    copy(lineColor = Some(newLineColor))
  def lineColor(r: Int, g: Int, b: Int, a: Double): MarkerOptions =
    lineColor(Color.rgba(r, g, b, a))
  def lineColor(r: Int, g: Int, b: Int): MarkerOptions =
    lineColor(r, g, b, 1.0)
}

object MarkerOptions {
  def apply(): MarkerOptions = MarkerOptions(
    size = None,
    color = None,
    symbol = None,
    lineWidth = None,
    lineColor = None
  )
}


object DashMode extends Enumeration {
  val Dash = Value("dash")
  val Dot = Value("dot")
  val DashDot = Value("dashdot")
}


case class LineOptions(
  color: Option[Color],
  width: Option[Int],
  dashMode: Option[DashMode.Value]
) {

  def width(newWidth: Int): LineOptions = copy(width = Some(newWidth))

  def color(newColor: Color): LineOptions =
    copy(color = Some(newColor))
  def color(r: Int, g: Int, b: Int, a: Double): LineOptions =
    color(Color.rgba(r, g, b, a))
  def color(r: Int, g: Int, b: Int): LineOptions =
    color(r, g, b, 1.0)

  def dashMode(newDashMode: DashMode.Value): LineOptions =
    copy(dashMode = Some(newDashMode))
  def dashMode(newDashMode: String): LineOptions =
    dashMode(DashMode.withName(newDashMode))
}


object LineOptions {
  def apply(): LineOptions = LineOptions(
    color = None,
    width = None,
    dashMode = None
  )
}
