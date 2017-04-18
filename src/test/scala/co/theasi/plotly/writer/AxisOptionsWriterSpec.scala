package co.theasi.plotly.writer

import org.scalatest.{FlatSpec, Matchers}

import org.json4s.JString

import co.theasi.plotly.{AxisOptions, AxisType}

class AxisOptionsWriterSpec extends FlatSpec with Matchers {
  "toJson" should "serialize the plot type" in {
    val options = AxisOptions().axisType(AxisType.Log)
    val jobj = AxisOptionsWriter.toJson(options)
    jobj \ "type" shouldEqual JString("log")
  }
}
