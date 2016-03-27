package co.theasi.plotly

import org.scalatest._

class ScatterOptionsSpec extends FlatSpec with Matchers {

  "ScatterOptions" should "support setting mode" in {
    import ScatterMode._
    val opts0 = ScatterOptions().mode(Marker, Line)
    opts0.mode should contain allOf (Marker, Line)
    val opts1 = ScatterOptions().mode(Seq(Marker, Text))
    opts1.mode should contain allOf (Marker, Text)
  }

  "MarkerOptions" should "support setting color" in {
    val opts0 = MarkerOptions().color(Color.rgba(1, 2, 3, 0.2))
    opts0.color.get shouldEqual Color.rgba(1, 2, 3, 0.2)
    val opts1 = MarkerOptions().color(10, 11, 12, 0.4)
    opts1.color.get shouldEqual Color.rgba(10, 11, 12, 0.4)
    val opts2 = MarkerOptions().color(20, 21, 22)
    opts2.color.get shouldEqual Color.rgb(20, 21, 22)
  }

  it should "support setting the line color" in {
    val opts0 = MarkerOptions().lineColor(Color.rgba(1, 2, 3, 0.2))
    opts0.lineColor.get shouldEqual Color.rgba(1, 2, 3, 0.2)
    val opts1 = MarkerOptions().lineColor(10, 11, 12, 0.4)
    opts1.lineColor.get shouldEqual Color.rgba(10, 11, 12, 0.4)
    val opts2 = MarkerOptions().lineColor(20, 21, 22)
    opts2.lineColor.get shouldEqual Color.rgb(20, 21, 22)
  }

}
