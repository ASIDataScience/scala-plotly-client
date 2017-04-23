package co.theasi.plotly

case class BarOptions(
  name: Option[String] = None,
  marker: MarkerOptions = MarkerOptions()
) extends SeriesOptions {

  /** Set the name of the series */
  def name(newName: String): BarOptions = copy(name = Some(newName))

  /** Set new [[MarkerOptions]] for this series.
    *
    * @see [[BarOptions.updatedMarker]] to update an
    *   existing set of marker options.
    */
  def marker(newMarker: MarkerOptions): BarOptions =
    copy(marker = newMarker)

  /** Update the [[MarkerOptions]] for this series.
    *
    * @param updater Function mapping the existing [[MarkerOptions]]
    *   to new [[MarkerOptions]].
    *
    * @example {{{
    * val xs = (1 to 10)
    * val ys = (1 to 10)
    *
    * val p = CartesianPlot()
    *   .withBar(xs, ys, BarOptions().updatedMarker(_.size(10).symbol("x")))
    * }}}
    */
  def updatedMarker(updater: MarkerOptions => MarkerOptions)
  : BarOptions = {
    val newMarker = updater(marker)
    marker(newMarker)
  }
}
