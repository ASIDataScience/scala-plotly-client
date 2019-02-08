package ai.faculty.plotly

sealed trait Colorscale

/** Colorscale predefined by Plotly */
case class ColorscalePredef(s: String) extends Colorscale
