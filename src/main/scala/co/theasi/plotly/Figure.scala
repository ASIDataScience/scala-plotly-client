package co.theasi.plotly

trait Figure {
  type Self <: Figure

  def plots: Vector[Plot]
  def viewPorts: Vector[ViewPort]
  def options: FigureOptions

  def withNewOptions(newOptions: FigureOptions): Self

  /** Set legend options
    *
    * {{{
    * val legend = LegendOptions().fontSize(20)
    * val figure = Figure().legend(legend)
    * }}}
    */
  def legend(newLegend: LegendOptions): Self =
    withNewOptions(options.copy(legendOptions = newLegend))

  /** Set figure title */
  def title(newTitle: String): Self =
    withNewOptions(options.copy(title = Some(newTitle)))

  private def margins(newMargins: Margins): Self =
    withNewOptions(options.copy(margins = newMargins))

  /** Set layout margins */
  def margins(top: Int, right: Int, bottom: Int, left: Int): Self =
    margins(Margins(Some(top), Some(right), Some(bottom), Some(left)))

  /** Set left margin (in px) */
  def leftMargin(newLeftMargin: Int): Self = {
    val newMargins = options.margins.copy(left = Some(newLeftMargin))
    margins(newMargins)
  }
  /** Set right margin (in px) */
  def rightMargin(newRightMargin: Int): Self = {
    val newMargins = options.margins.copy(right = Some(newRightMargin))
    margins(newMargins)
  }
  /** Set top margin (in px) */
  def topMargin(newTopMargin: Int): Self = {
    val newMargins = options.margins.copy(top = Some(newTopMargin))
    margins(newMargins)
  }
  /** Set bottom margin (in px) */
  def bottomMargin(newBottomMargin: Int): Self = {
    val newMargins = options.margins.copy(bottom = Some(newBottomMargin))
    margins(newMargins)
  }

  /** Set the plot width (in px) */
  def width(newWidth: Int): Self =
    withNewOptions(options.copy(width = Some(newWidth)))

  /** Set the plot height (in px) */
  def height(newHeight: Int): Self =
    withNewOptions(options.copy(height = Some(newHeight)))

  /** Set the paper's background color
    *
    * {{{
    * val figure = Figure().paperBackgroundColor(Color.rgb(0, 255, 0))
    * }}}
    */
  def paperBackgroundColor(newColor: Color): Self =
    withNewOptions(options.copy(paperBackgroundColor = Some(newColor)))
  /** Set the paper's background color with (red, green, blue, alpha) */
  def paperBackgroundColor(r: Int, g: Int, b: Int, a: Double): Self =
    paperBackgroundColor(Color.rgba(r, g, b, a))
  /** Set the paper's background color. */
  def paperBackgroundColor(r: Int, g: Int, b: Int): Self =
    paperBackgroundColor(Color.rgb(r, g, b))

  /** Set the plot's background color
    *
    * {{{
    * val figure = Figure().plotBackgroundColor(Color.rgb(0, 255, 0))
    * }}}
    */
  def plotBackgroundColor(newColor: Color): Self =
    withNewOptions(options.copy(plotBackgroundColor = Some(newColor)))
  /** Set the plot's background color with (red, green, blue, alpha) */
  def plotBackgroundColor(r: Int, g: Int, b: Int, a: Double): Self =
    plotBackgroundColor(Color.rgba(r, g, b, a))
  /** Set the plot's background color */
  def plotBackgroundColor(r: Int, g: Int, b: Int): Self =
    plotBackgroundColor(Color.rgb(r, g, b))


}

object Figure {
  def apply(): SinglePlotFigure = SinglePlotFigure()
}


/** Figure containing a single plot
  *
  * {{{
  * val xs = Vector(1, 2, 5)
  * val ys = Vector(5, 9, 11)
  *
  * val figure = SinglePlotFigure()
  *   .plot { CartesianPlot().withScatter(xs, ys) }
  *   .title("My awesome plot")
  *
  * draw(figure, "test-plot")
  * }}}
  *
  * Use the companion object's `apply` method to construct
  * a new figure. Set the content of the plot on the figure
  * with the [[plot]] method.
  *
  * '''Note''' that all the methods on this class return a
  * ''copy'' of the current instance. They do not modify the
  *  instance in place. Instances of `SinglePlotFigure`
  *  are immutable.
  *
  */
case class SinglePlotFigure(plot: Plot, options: FigureOptions)
extends Figure {

  type Self = SinglePlotFigure

  def plots = Vector(plot)
  def viewPorts = Vector(ViewPort((0.0, 1.0), (0.0, 1.0)))

  /** Set the content of the figure.
    *
    * This returns a ''new'' figure containing the plot instance.
    */
  def plot(newPlot: Plot): SinglePlotFigure = copy(plot = newPlot)

  def withNewOptions(newOptions: FigureOptions): Self =
    copy(options = newOptions)

}

/** Factory methods for building instances of `SinglePlotFigure`.
  */
object SinglePlotFigure {

  /** Build a [[SinglePlotFigure]] with default options
    *
    * This factory method does not allow customisation of the figure.
    * The intention is that customisation happens through methods
    * on the `SinglePlotFigure` instance:
    *
    * {{{
    * val figure = SinglePlotFigure()
    *   .title("my figure")
    *   .legend(LegendOptions().x(0.5).y(0.1))
    *   .paperBackgroundColor(254, 247, 234)
    *   .plotBackgroundColor(254, 247, 234)
    * }}}
    */
  def apply(): SinglePlotFigure = SinglePlotFigure(Plot(), FigureOptions())

}


/** Figure containing plots arranged on a grid.
  *
  * This [[Figure]] subclass is designed for equally spaced subplots on a grid.
  * Use the companion object's `apply` method to construct a new instance,
  * specifying the number of rows and columns. For instance,
  * `val figure = GridFigure(2, 3)` will build a new figure with 6 subplots,
  * arranged in a grid with two rows and three columns. You can then use
  * the [[plot]] method to set the content of specific sub-plots.
  *
  * {{{
  * import util.Random
  * val xs = (0 to 100).map { i => Random.nextGaussian }
  * val ys = (0 to 100).map { i => Random.nextGaussian }
  * val ys2 = (0 to 100).map { i => Random.nextGaussian }
  * val ys3 = (0 to 100).map { i => Random.nextGaussian }
  *
  * val figure = GridFigure(2, 3) // 2 rows, 3 columns
  *   .plot(0, 0) { CartesianPlot().withScatter(xs, ys) } // top left
  *   .plot(0, 1) { CartesianPlot().withScatter(xs, ys2) } // top middle
  *   .plot(1, 2) { CartesianPlot().withScatter(xs, ys3) } // bottom right
  *   .title("My grid figure")
  *
  * draw(figure, "grid-figure")
  * }}}
  *
  */
case class GridFigure(
    plots: Vector[Plot],
    viewPorts: Vector[ViewPort],
    numberRows: Int,
    numberColumns: Int,
    options: FigureOptions)
extends Figure {

  type Self = GridFigure

  /** Set the content of a sub-plot.
    *
    * This returns a ''copy'' of the current figure with the new plot.
    */
  def plot(rowIndex: Int, columnIndex: Int)(newPlot: Plot): GridFigure = {
    val ref = subplotRef(rowIndex, columnIndex)
    copy(plots = plots.updated(ref, newPlot))
  }

  /** Get the plot at a specific `row, column`. */
  def plotAt(rowIndex: Int, columnIndex: Int): Plot =
    plots(subplotRef(rowIndex, columnIndex))

  /** Get the view-port at a specific `row, column`. */
  def viewPortAt(rowIndex: Int, columnIndex: Int): ViewPort = {
    val ref = subplotRef(rowIndex, columnIndex)
    viewPorts(ref)
  }

  def withNewOptions(newOptions: FigureOptions): Self =
    copy(options = newOptions)

  private def subplotRef(rowIndex: Int, columnIndex: Int): Int = {
    checkRowColumn(rowIndex, columnIndex)
    rowColumnToRefImpl(rowIndex, columnIndex)
  }

  private def rowColumnToRefImpl(row: Int, column: Int): Int = {
    val value = row*numberColumns + column
    value
  }

  private def checkRowColumn(row: Int, column: Int) {
    checkRow(row)
    checkColumn(column)
  }

  private def checkRow(row: Int) {
    if (row >= numberRows) {
      throw new IllegalArgumentException(s"Row index $row out of bounds.")
    }
  }

  private def checkColumn(column: Int) {
    if (column >= numberColumns) {
      throw new IllegalArgumentException(s"Column index $column out of bounds.")
    }
  }
}


/** Factory methods for building instances of `GridFigure`.
  */
object GridFigure {

  val DefaultHorizontalSpacing = 0.2
  val DefaultVerticalSpacing = 0.3

  /** Build a [[GridFigure]] with default options.
    *
    * This factory method does not allow customisation of the figure.
    * The intention is that customisation happens through methods
    * on the `GridFigure` instance:
    *
    * {{{
    * val figure = GridFigure(1, 2)
    *   .title("my figure")
    *   .paperBackgroundColor(254, 247, 234)
    *   .plotBackgroundColor(254, 247, 234)
    * }}}
    */
  def apply(numberRows: Int, numberColumns: Int): GridFigure = {

    // Spacing between plots
    val horizontalSpacing = DefaultHorizontalSpacing / numberColumns.toDouble
    val verticalSpacing = DefaultVerticalSpacing / numberRows.toDouble

    // plot width
    val width =
      (1.0 - horizontalSpacing * (numberColumns - 1))/numberColumns.toDouble

    // plot height
    val height =
      (1.0 - verticalSpacing * (numberRows - 1))/numberRows.toDouble

    val xDomains = (0 until numberColumns).map { icol =>
      val start = icol * (width + horizontalSpacing)
      val end = start + width
      (start, end)
    }

    val yDomains = (0 until numberRows).map { irow =>
      val top = 1.0 - (irow * (height + verticalSpacing))
      val bottom = top - height
      (bottom, top)
    }

    // Cartesian product of xDomains and yDomains
    val viewPorts = for {
      yDomain <- yDomains
      xDomain <- xDomains
    } yield ViewPort(xDomain, yDomain)

    val plots = Vector.fill(viewPorts.size) { Plot() }

    GridFigure(plots, viewPorts.toVector,
      numberRows, numberColumns, FigureOptions())
  }
}


/** Figure containing plots arranged in a row.
  *
  * This [[Figure]] subclass is designed for equally spaced subplots
  * in a row.
  * Use the companion object's `apply` method to construct a new
  * instance, specifying the number of subplots. For instance,
  * `val figure = RowFigure(2)` will build a new figure with two
  * subplots in a row. You can then use the [[plot]] method to
  *  set the content of specific sub-plots.
  *
  * {{{
  * import util.Random
  * val xs = (0 to 100).map { i => Random.nextGaussian }
  * val ys = (0 to 100).map { i => Random.nextGaussian }
  * val ys2 = (0 to 100).map { i => Random.nextGaussian }
  *
  * val figure = RowFigure(2) // 2 subplots
  *   .plot(0) { CartesianPlot().withScatter(xs, ys) } // left
  *   .plot(1) { CartesianPlot().withScatter(xs, ys2) } // right
  *   .title("My row figure")
  *
  * draw(figure, "row-figure")
  * }}}
  */
case class RowFigure(impl: GridFigure)
extends Figure {

  type Self = RowFigure

  def plots = impl.plots
  def viewPorts = impl.viewPorts
  def options = impl.options

  /** Set the content of a sub-plot.
    *
    * This returns a ''new'' figure containing the plot instance.
    */
  def plot(index: Int)(newPlot: Plot): RowFigure = {
    val newImpl = impl.plot(0, index)(newPlot)
    RowFigure(newImpl)
  }

  def withNewOptions(newOptions: FigureOptions): Self = {
    val newImpl = impl.withNewOptions(newOptions)
    RowFigure(newImpl)
  }

}


object RowFigure {

  /** Build a [[RowFigure]] with default options.
    *
    * This factory method does not allow customisation of the figure.
    * The intention is that customisation happens through methods
    * on the `RowFigure` instance:
    *
    * {{{
    * val figure = RowFigure(2)
    *   .title("my figure")
    *   .paperBackgroundColor(254, 247, 234)
    *   .plotBackgroundColor(254, 247, 234)
    * }}}
    */
  def apply(numberColumns: Int): RowFigure = {
    val impl = GridFigure(1, numberColumns)
    RowFigure(impl)
  }
}
