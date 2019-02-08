package ai.faculty.plotly

/** Options controlling how surface plots are drawn.
  *
  * {{{
  * val surfaceOptions = SurfaceOptions().opacity(0.9).colorscale("Electric")
  *
  * val plot = ThreeDPlot().withSurface(xs, ys, zs, surfaceOptions)
  * }}}
  */
case class SurfaceOptions(
  name: Option[String],
  opacity: Option[Double],
  showScale: Option[Boolean],
  colorscale: Option[Colorscale]
) extends SeriesOptions {

  /** Set the name of the series */
  def name(newName: String): SurfaceOptions =
    copy(name = Some(newName))

  /** Set the surface opacity.
    *
    * @param newOpacity Opacity value; must be between 0 and 1.
    */
  def opacity(newOpacity: Double): SurfaceOptions = {
    require(
      newOpacity >= 0.0 && newOpacity <= 1.0,
      "Opacity must be between 0 and 1")
    copy(opacity = Some(newOpacity))
  }

  /** Draw a color bar on the side of the plot mapping from color to z-value */
  def withScale(): SurfaceOptions = copy(showScale = Some(true))

  /** Hide color bar on the side of the plot mapping from color to z-value */
  def noScale(): SurfaceOptions = copy(showScale = Some(false))

  /** Set the colorscale.
    *  A list of predefined colorscales is available at
    * [[https://github.com/plotly/plotly.js/blob/master/src/components/colorscale/scales.js]]
    *
    * @param newColorscale Colorscale name
    */
  def colorscale(newColorscale: String): SurfaceOptions =
    copy(colorscale = Some(ColorscalePredef(newColorscale)))
}


object SurfaceOptions {
  def apply(): SurfaceOptions = SurfaceOptions(
    name = None,
    opacity = None,
    showScale = None,
    colorscale = None
  )
}
