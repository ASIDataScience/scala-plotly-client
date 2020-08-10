package co.theasi.plotly.writer

import co.theasi.plotly.GeoCartesianPlot
import org.json4s
import org.json4s.{JDouble, JString}
import org.json4s.JsonAST.JObject
import org.json4s.JsonDSL.{jobject2assoc, pair2Assoc}

object GeoLayoutWriter {
  def toJson(p: GeoCartesianPlot): JObject = {
    val center = ("lat" -> JDouble(p.centerLat)) ~ ("lon" -> JDouble(p.centerLon))
    val frame = ("center" -> center) ~ ("zoom" -> JDouble(p.zoom))
    val body = if (p.token.isDefined) frame ~ ("accesstoken" -> JString(p.token.get)) else frame

    json4s.JObject("mapbox" -> body)
  }
}
