package ai.faculty.plotly

case class Font(
  color: Option[Color],
  family: Option[List[String]],
  size: Option[Int]
)

object Font {
  def apply(): Font = Font(None, None, None)
}

object emptyFont {
  def unapply(font: Font): Boolean = font == Font()
}
