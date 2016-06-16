package co.theasi.plotly.writer

import org.scalatest._

import org.json4s._

import co.theasi.plotly.{ SurfaceOptions, BarOptions }

class SeriesWriterSpec extends FlatSpec with Matchers {

  "toJson" should "serialize the srcs and scenes for surfaces" in {
    val srcs = List("src1")
    val sceneIndex = 2
    val options = SurfaceOptions()
    val writeInfo = SurfaceZWriteInfo(srcs, sceneIndex, options)

    val jobj = SeriesWriter.toJson(writeInfo)
    (jobj \ "zsrc") shouldEqual JString("src1")
    (jobj \ "scene") shouldEqual JString("scene2")
    (jobj \ "type") shouldEqual JString("surface")
  }

  it should "serialize the srcs and scenes for xyz-surfaces" in {
    val srcs = List("x-src", "y-src", "z-src")
    val sceneIndex = 3
    val options = SurfaceOptions()
    val writeInfo = SurfaceXYZWriteInfo(srcs, sceneIndex, options)

    val jobj = SeriesWriter.toJson(writeInfo)
    (jobj \ "xsrc") shouldEqual JString("x-src")
    (jobj \ "ysrc") shouldEqual JString("y-src")
    (jobj \ "zsrc") shouldEqual JString("z-src")
    (jobj \ "scene") shouldEqual JString("scene3")
    (jobj \ "type") shouldEqual JString("surface")
  }

  it should "serialize the srcs and axisIndex for bar" in {
    val srcs = List("xsrc", "ysrc")
    val axisIndex = 1
    val options = BarOptions()
    val writeInfo = BarWriteInfo(srcs, axisIndex, options)

    val jobj = SeriesWriter.toJson(writeInfo)
    (jobj \ "xsrc") shouldEqual JString("xsrc")
    (jobj \ "ysrc") shouldEqual JString("ysrc")
    (jobj \ "xaxis") shouldEqual JString("x")
    (jobj \ "yaxis") shouldEqual JString("y")
    (jobj \ "type") shouldEqual JString("bar")
  }

}
