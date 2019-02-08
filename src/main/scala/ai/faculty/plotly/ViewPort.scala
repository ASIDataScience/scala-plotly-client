package ai.faculty.plotly

case class ViewPort(
  xDomain: (Double, Double),
  yDomain: (Double, Double)
)


object ViewPort {
  def apply(): ViewPort = ViewPort((0.0, 1.0), (0.0, 1.0))
}
