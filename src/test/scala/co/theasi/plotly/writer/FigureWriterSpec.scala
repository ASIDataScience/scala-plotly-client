package co.theasi.plotly.writer

import co.theasi.plotly._
import org.json4s._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FigureWriterSpec extends AnyFlatSpec with Matchers with Inside {

  val testZData = Vector(Vector(1, 2, 3), Vector(4, 5, 6))
  val testZData2 = Vector(Vector(1, 2, 4), Vector(5, 8, 9))

  "plotAsJson" should "serialize grid layouts with 3D plots" in {
    val figure = GridFigure(2, 1)
      .plot(0, 0) { ThreeDPlot().withSurface(testZData) }
      .plot(1, 0) { ThreeDPlot().withSurface(testZData2)}

    val columnUidMap = Map(
      "z-0-0" -> "uid1",
      "z-0-1" -> "uid2",
      "z-0-2" -> "uid3",
      "z-1-0" -> "uid4",
      "z-1-1" -> "uid5",
      "z-1-2" -> "uid6"
    )

    val drawnGrid = GridFile("file-id", "file-name", columnUidMap)

    val jobj = FigureWriter.plotAsJson(figure, drawnGrid, "test-file")
    val figobj = jobj \ "figure"

    // Check that the surfaces get plotted on the correct scenes
    val JArray(data) = figobj \ "data"
    data(0) \ "zsrc" shouldEqual JString("file-id:uid1,uid2,uid3")
    data(1) \ "zsrc" shouldEqual JString("file-id:uid4,uid5,uid6")
    data(0) \ "scene" shouldEqual JString("scene")
    data(1) \ "scene" shouldEqual JString("scene2")

    // Check that the scenes are in the correct place
    val layout = figobj \ "layout"

    List("scene", "scene2").foreach { scene =>
      inside(layout \ scene \ "domain" \ "x") {
        case JArray(List(JDouble(start), JDouble(finish))) =>
          start shouldEqual 0.0
          finish shouldEqual 1.0
      }
    }

    inside(layout \ "scene" \ "domain" \ "y") {
      case JArray(List(JDouble(start), JDouble(finish))) =>
        start should be >= 0.5
        finish shouldEqual 1.0 +- 1e-5
    }
    inside(layout \ "scene2" \ "domain" \ "y") {
      case JArray(List(JDouble(start), JDouble(finish))) =>
        start shouldEqual 0.0 +- 1e-5
        finish should be <= 0.5
    }

  }

  it should "serialize simple 3D layouts with multiple surfaces" in {

    val figure = SinglePlotFigure().plot {
      ThreeDPlot().withSurface(testZData).withSurface(testZData2)
    }

    val columnUidMap = Map(
      "z-0-0" -> "uid1",
      "z-0-1" -> "uid2",
      "z-0-2" -> "uid3",
      "z-1-0" -> "uid4",
      "z-1-1" -> "uid5",
      "z-1-2" -> "uid6"
    )

    val drawnGrid = GridFile("file-id", "file-name", columnUidMap)

    val jobj = FigureWriter.plotAsJson(figure, drawnGrid, "test-file")
    val figobj = jobj \ "figure"

    // Check that the surfaces get plotted on the correct scenes
    val JArray(data) = figobj \ "data"
    data(0) \ "zsrc" shouldEqual JString("file-id:uid1,uid2,uid3")
    data(1) \ "zsrc" shouldEqual JString("file-id:uid4,uid5,uid6")
    data(0) \ "scene" shouldEqual JString("scene")
    data(1) \ "scene" shouldEqual JString("scene")

  }

}
