package ai.faculty.plotly.writer

import ai.faculty.plotly.{Plot, Grid, Figure, SinglePlotFigure, FigureOptions}

trait ServerWriterWithDefaultCredentials extends ServerWriter {
  implicit val server = ServerWithDefaultCredentials
}

trait ServerWriter {
  implicit val server: Server

  def draw(
      figure: Figure,
      fileName: String)
      (implicit server: Server)
  : PlotFile = {
    draw(figure, fileName, FileOptions())
  }

  def draw(
    figure: Figure,
    fileName: String,
    fileOptions: FileOptions)
    (implicit server: Server)
  : PlotFile = {
    FigureWriter.draw(figure, fileName, fileOptions)
  }

  def draw(
      plot: Plot,
      fileName: String)
      (implicit server: Server)
  : PlotFile = {
    draw(plot, fileName, FileOptions())
  }

  def draw(
      plot: Plot,
      fileName: String,
      fileOptions: FileOptions)
      (implicit server: Server)
  : PlotFile = {
    val figure = SinglePlotFigure(plot, FigureOptions())
    draw(figure, fileName, fileOptions)
  }

  def draw(
      grid: Grid,
      fileName: String)
      (implicit server: Server)
  : GridFile = {
    draw(grid, fileName, FileOptions())
  }

  def draw(
      grid: Grid,
      fileName: String,
      fileOptions: FileOptions)
      (implicit server: Server)
  : GridFile = {
    GridWriter.draw(grid, fileName, fileOptions)
  }

}
