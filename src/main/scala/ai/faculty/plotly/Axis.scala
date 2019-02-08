package ai.faculty.plotly

case class Axis(
  options: AxisOptions
)

object Axis {
  def apply(): Axis = Axis(AxisOptions())
}
