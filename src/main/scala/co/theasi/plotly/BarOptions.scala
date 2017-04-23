package co.theasi.plotly

case class BarOptions(
  name: Option[String] = None,
  marker: MarkerOptions = MarkerOptions()
) extends SeriesOptions {
  def name(newName: String): BarOptions = copy(name = Some(newName))

  def marker(newMarker: MarkerOptions): BarOptions =
    copy(marker = newMarker)

  def updatedMarker(updater: MarkerOptions => MarkerOptions)
  : BarOptions = {
    val newMarker = updater(marker)
    marker(newMarker)
  }
}
