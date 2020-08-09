package co.theasi.plotly.writer

import org.json4s.{JNothing, JString}
import co.theasi.plotly.{AxisOptions, AxisType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AxisOptionsWriterSpec extends AnyFlatSpec with Matchers {
  "toJson" should "serialize the plot type" in {
    val options = AxisOptions().axisType(AxisType.Log)
    val jobj = AxisOptionsWriter.toJson(options)
    jobj \ "type" shouldEqual JString("log")
  }

  it should "serialize to null if the plot type is not specified" in {
    val options = AxisOptions()
    val jobj = AxisOptionsWriter.toJson(options)
    jobj \ "type" shouldEqual JNothing
  }
}
