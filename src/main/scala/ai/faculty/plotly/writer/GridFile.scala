package ai.faculty.plotly.writer

import org.json4s._

case class GridFile(
    fileId: String,
    fileName: String = "",
    columnUids: Map[String, String] = Map.empty)


object GridFile {
  def fromFileName(fileName: String)(implicit server: Server)
  : GridFile = {
    val request = Api.get("grids/lookup", Seq("path" -> fileName))
    val response = Api.despatchAndInterpret(request)
    fromResponse(response)
  }

  def fromResponse(response: JValue): GridFile = {
    val JString(fid) = (response \ "fid")
    val JString(fileName) = (response \ "filename")
    val JArray(columns) = (response \ "cols")
    val columUids = columns.map { col =>
      val JString(name) = (col \ "name")
      val JString(uid) = (col \ "uid")
      name -> uid
    }.toMap
    new GridFile(fid, fileName, columUids)
  }
}
