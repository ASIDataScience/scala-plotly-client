package ai.faculty.plotly.writer

import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._

import scala.util.{ Try, Success, Failure }

import ai.faculty.plotly.Grid

object GridWriter {

  def draw(
      grid: Grid,
      fileName: String,
      fileOptions: FileOptions = FileOptions()
  )(implicit server: Server): GridFile = {
    if(fileOptions.overwrite) { deleteIfExists(fileName) }
    val request = Api.post("grids",
      compact(render(gridAsJson(grid, fileName))))
    val parsedResponse = Api.despatchAndInterpret(request)
    GridFile.fromResponse(parsedResponse \ "file")
  }

  private def gridAsJson(grid: Grid, fileName: String): JObject = {
    val columnsAsJson = grid.columns.toIterator.zipWithIndex.map {
      case ((columnName, columnValues), index) =>
        ColumnWriter.toJson(columnValues, columnName, index)
    }
    val jsonObj = columnsAsJson.reduce {
      (memo, current) => memo ~ current
    }
    (
      ("data" -> ("cols" -> jsonObj)) ~
      ("filename" -> fileName)
    )
  }

  private def deleteIfExists(fileName: String)(implicit server: Server) {
    Try { GridFile.fromFileName(fileName) } match {
      case Success(grid) => // exists already -> delete
        Api.despatchAndInterpret(Api.delete(s"grids/${grid.fileId}"))
      case Failure(PlotlyException("Not found.")) => // good to go
      case Failure(e) => throw e // some other error -> re-throw
    }
  }

}
