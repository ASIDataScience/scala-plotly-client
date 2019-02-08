package ai.faculty.plotly.writer

import org.json4s._
import org.json4s.JsonDSL._

import ai.faculty.plotly.{PType, PInt, PDouble, PString}

object ColumnWriter {

  def toJson[X <: PType](
      column: Iterable[X],
      columnName: String,
      order: Int)
  : JObject = {
    val xsAsJson = column.map { ptypeToJson(_) }
    val data = (
      columnName -> (("data" -> xsAsJson) ~ ("order" -> order))
    )
    data
  }

  def ptypeToJson[X <: PType](x: X): JValue = x match {
    case PInt(i) => JInt(i)
    case PDouble(d) => JDouble(d)
    case PString(s) => JString(s)
  }

}
