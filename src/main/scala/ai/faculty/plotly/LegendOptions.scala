package ai.faculty.plotly

case class LegendOptions(
  x: Option[Double],
  y: Option[Double],
  xAnchor: Option[XAnchor.Value],
  yAnchor: Option[YAnchor.Value],
  borderColor: Option[Color],
  backgroundColor: Option[Color],
  font: Font,
  borderWidth: Option[Double]
) {
  def x(newX: Double): LegendOptions = copy(x = Some(newX))
  def y(newY: Double): LegendOptions = copy(y = Some(newY))

  def xAnchor(newXAnchor: XAnchor.Value): LegendOptions =
    copy(xAnchor = Some(newXAnchor))
  def yAnchor(newYAnchor: YAnchor.Value): LegendOptions =
    copy(yAnchor = Some(newYAnchor))

  def borderColor(newColor: Color): LegendOptions =
    copy(borderColor = Some(newColor))

  def fontColor(newColor: Color): LegendOptions =
    copy(font = font.copy(color = Some(newColor)))
  def fontSize(newSize: Int): LegendOptions =
    copy(font = font.copy(size = Some(newSize)))
  def fontFamily(newFamily: List[String]): LegendOptions =
    copy(font = font.copy(family = Some(newFamily)))

  def borderWidth(newWidth: Double): LegendOptions =
    copy(borderWidth = Some(newWidth))
}

object LegendOptions {
  def apply(): LegendOptions = LegendOptions(
    x = None,
    y = None,
    xAnchor = None,
    yAnchor = None,
    borderColor = None,
    backgroundColor = None,
    font = Font(),
    borderWidth = None
  )
}

object emptyLegendOptions {
  def unapply(options: LegendOptions): Boolean =
    options == LegendOptions()
}

object XAnchor extends Enumeration {
  val Left = Value("left")
  val Center = Value("center")
  val Right = Value("right")
}

object YAnchor extends Enumeration {
  val Top = Value("top")
  val Middle = Value("middle")
  val Bottom = Value("bottom")
}
