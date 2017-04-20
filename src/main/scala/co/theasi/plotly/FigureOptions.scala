package co.theasi.plotly

case class FigureOptions(
  title: Option[String],
  legendOptions: LegendOptions,
  showLegend: Option[Boolean],
  margins: Margins,
  width: Option[Int],
  height: Option[Int],
  paperBackgroundColor: Option[Color],
  plotBackgroundColor: Option[Color]
)


object FigureOptions {
  def apply(): FigureOptions = FigureOptions(
    title = None,
    legendOptions = LegendOptions(),
    showLegend = None,
    margins = Margins(),
    width = None,
    height = None,
    paperBackgroundColor = None,
    plotBackgroundColor = None
  )
}
