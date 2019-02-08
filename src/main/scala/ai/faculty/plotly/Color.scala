package ai.faculty.plotly

case class Color(
  r: Int,
  g: Int,
  b: Int,
  a: Double
)

case object Color {
  def rgb(r: Int, g: Int, b: Int): Color = Color(r, g, b, 1.0)
  def rgba(r: Int, g: Int, b: Int, a: Double): Color =
    Color(r, g, b, a)
}
