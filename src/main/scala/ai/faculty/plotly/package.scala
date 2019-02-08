package ai.faculty

/** ==Scala Plotly client==
  *
  * Plotly is a platform for creating and hosting plots. This
  * library lets you create, modify and delete plots on Plotly.
  *
  * Build a plot using [[Plot]], and send that
  * plot to the Plotly servers with [[draw]].
  *
  * {{{
  * import ai.faculty.plotly._
  *
  * val xs = Vector(1, 2, 3)
  * val ys = Vector(4.5, 8.5, 21.0)
  *
  * val p = Plot().withScatter(xs, ys)
  * draw(p, "my-first-plot")
  * }}}
  *
  * ==Useful links==
  *
  * For more information on how to build complex plot objects,
  * read the documentation for [[CartesianPlot]] and [[ThreeDPlot]].
  *
  * For information on structuring complex figures with subplots,
  * read the documentation for [[RowFigure]] and [[GridFigure]].
  *
  * To control how the client deals with existing files with
  * the same name on the Plotly server, and to control the
  * privacy options, read the documentation
  * for [[writer.FileOptions]]
  */
package object plotly
extends WritableImplicits
with ReadableImplicits
with writer.ServerWriterWithDefaultCredentials {

  sealed trait PType
  case class PString(s: String) extends PType
  case class PInt(i: Int) extends PType
  case class PDouble(d: Double) extends PType

}
