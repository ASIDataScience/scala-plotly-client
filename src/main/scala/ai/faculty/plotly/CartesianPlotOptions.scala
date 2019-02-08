package ai.faculty.plotly

case class CartesianPlotOptions(xAxis: Axis, yAxis: Axis)


object CartesianPlotOptions {
  def apply(): CartesianPlotOptions =
    CartesianPlotOptions(Axis(), Axis())
}
