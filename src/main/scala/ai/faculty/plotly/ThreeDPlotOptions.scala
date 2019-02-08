package ai.faculty.plotly

case class ThreeDPlotOptions(
  xAxisOptions: AxisOptions,
  yAxisOptions: AxisOptions,
  zAxisOptions: AxisOptions
)

object ThreeDPlotOptions {
  def apply(): ThreeDPlotOptions = ThreeDPlotOptions(
    AxisOptions(),
    AxisOptions(),
    AxisOptions()
  )
}
