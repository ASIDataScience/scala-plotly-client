package ai.faculty.plotly.writer

import org.json4s._

import org.scalatest._

import ai.faculty.plotly.{Font, Color}

class FontWriterSpec extends FlatSpec with Matchers {
  "FontWriter.toJson" should "serialize the font color" in {
    val testColor = Color.rgb(10, 20, 30)
    val font = Font().copy(color = Some(testColor))
    val jobj = FontWriter.toJson(font).get
    jobj \ "color" shouldEqual JString(ColorWriter.toJson(testColor))
  }

  it should "serialize the font size" in {
    val font = Font().copy(size = Some(24))
    val jobj = FontWriter.toJson(font).get
    jobj \ "size" shouldEqual JInt(24)
  }

  it should "serialize the font family" in {
    val font = Font().copy(family=Some(List("Droid Sans Mono", "sans serif")))
    val jobj = FontWriter.toJson(font).get
    jobj \ "family" shouldEqual JString("\"Droid Sans Mono\", \"sans serif\"")
  }

  it should "return None for an empty font" in {
    val font = Font()
    FontWriter.toJson(font) shouldEqual None
  }
}
