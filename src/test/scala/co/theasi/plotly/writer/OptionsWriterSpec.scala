package co.theasi.plotly.writer

import co.theasi.plotly.{DashMode, LineOptions, ScatterOptions, SurfaceOptions}
import org.json4s._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OptionsWriterSpec extends AnyFlatSpec with Matchers {

  "surfaceOptionsToJson" should "serialize the colorscale if present" in {
    val surfaceOptions = SurfaceOptions().colorscale("Viridis")
    val jobj = OptionsWriter.surfaceOptionsToJson(surfaceOptions)
    (jobj \ "colorscale") shouldEqual JString("Viridis")
  }

  it should "omit the colorscale if absent" in {
    val surfaceOptions = SurfaceOptions()
    val jobj = OptionsWriter.surfaceOptionsToJson(surfaceOptions)
    (jobj \ "colorscale") shouldEqual JNothing
  }

  "scatterOptionsToJson" should "serialize line options" in {
    val lineOptions = LineOptions()
      .color(1, 2, 3, 0.5)
      .width(5)
      .dashMode(DashMode.Dot)
    val scatterOptions = ScatterOptions().line(lineOptions)
    val jobj = OptionsWriter.scatterOptionsToJson(scatterOptions)
    (jobj \ "line" \ "color") shouldEqual JString("rgba(1, 2, 3, 0.5)")
    (jobj \ "line" \ "width") shouldEqual JInt(5)
    (jobj \ "line" \ "dash") shouldEqual JString("dot")
  }

}
