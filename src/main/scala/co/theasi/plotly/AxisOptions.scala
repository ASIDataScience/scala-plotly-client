package co.theasi.plotly

case class AxisOptions(
  title: Option[String],
  tickLength: Option[Int],
  zeroLine: Option[Boolean],
  gridWidth: Option[Int],
  grid: Option[Boolean],
  line: Option[Boolean],
  lineColor: Option[Color],
  titleFont: Font,
  tickFont: Font,
  autoTick: Option[Boolean],
  tickSpacing: Option[Double],
  tickColor: Option[Color],
  axisType: Option[AxisType.Value],
  tickLabels: Option[Boolean]
) {
  def title(newTitle: String): AxisOptions = copy(title = Some(newTitle))

  def tickLength(newTickLength: Int): AxisOptions =
    copy(tickLength = Some(newTickLength))

  def withZeroLine: AxisOptions = copy(zeroLine = Some(true))
  def noZeroLine: AxisOptions = copy(zeroLine = Some(false))

  def gridWidth(newGridWidth: Int): AxisOptions =
    copy(gridWidth = Some(newGridWidth))

  def withGrid: AxisOptions = copy(grid = Some(true))
  def noGrid: AxisOptions = copy(grid = Some(false))

  def withLine: AxisOptions = copy(line = Some(true))
  def noLine: AxisOptions = copy(line = Some(false))

  def lineColor(newColor: Color): AxisOptions =
    copy(lineColor = Some(newColor))
  def lineColor(r: Int, g: Int, b: Int, a: Double): AxisOptions =
    lineColor(Color.rgba(r, g, b, a))
  def lineColor(r: Int, g: Int, b: Int): AxisOptions =
    lineColor(Color.rgb(r, g, b))

  def titleColor(newColor: Color): AxisOptions =
    copy(titleFont = titleFont.copy(color=Some(newColor)))
  def titleColor(r: Int, g: Int, b: Int, a: Double): AxisOptions =
    titleColor(Color.rgba(r, g, b, a))
  def titleColor(r: Int, g: Int, b: Int): AxisOptions =
    titleColor(Color.rgb(r, g, b))

  def tickFontColor(newColor: Color): AxisOptions =
    copy(tickFont = tickFont.copy(color=Some(newColor)))
  def tickFontColor(r: Int, g: Int, b: Int, a: Double): AxisOptions =
    tickFontColor(Color.rgba(r, g, b, a))
  def tickFontColor(r: Int, g: Int, b: Int): AxisOptions =
    tickFontColor(Color.rgb(r, g, b))

  def withAutoTick: AxisOptions = copy(autoTick = Some(true))
  def noAutoTick: AxisOptions = copy(autoTick = Some(false))

  def tickSpacing(newTickSpacing: Double): AxisOptions =
    copy(tickSpacing = Some(newTickSpacing))

  def tickColor(newColor: Color): AxisOptions =
    copy(tickColor = Some(newColor))
  def tickColor(r: Int, g: Int, b: Int, a: Double): AxisOptions =
    tickColor(Color.rgba(r, g, b, a))
  def tickColor(r: Int, g: Int, b: Int): AxisOptions =
    tickColor(Color.rgb(r, g, b))

  def withTickLabels: AxisOptions = copy(tickLabels = Some(true))
  def noTickLabels: AxisOptions = copy(tickLabels = Some(false))

  def axisType(typeValue: AxisType.Value): AxisOptions =
    copy(axisType = Some(typeValue))
  def axisType(typeValue: String): AxisOptions = {
    val typeValueEnum = AxisType.withName(typeValue)
    axisType(typeValueEnum)
  }
}

object AxisOptions {
  def apply(): AxisOptions = AxisOptions(
    title = None,
    tickLength = None,
    zeroLine = None,
    gridWidth = None,
    grid = None,
    line = None,
    lineColor = None,
    titleFont = Font(),
    tickFont = Font(),
    autoTick = None,
    tickSpacing = None,
    tickColor = None,
    axisType = None,
    tickLabels = None
  )
}
