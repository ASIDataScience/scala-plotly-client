package co.theasi.plotly

case class ContourOptions(
  name: Option[String],
  colorscale: Option[Colorscale]
) extends SeriesOptions {
  def name(newName: String): ContourOptions = copy(name = Some(newName))

  def colorscale(newColorscale: String) =
    copy(colorscale = Some(ColorscalePredef(newColorscale)))
}

object ContourOptions {
  def apply(): ContourOptions = ContourOptions(
    name = None,
    colorscale = None
  )
}
