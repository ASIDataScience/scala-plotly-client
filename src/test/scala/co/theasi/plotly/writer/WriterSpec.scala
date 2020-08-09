package co.theasi.plotly.writer

import co.theasi.plotly._
import org.json4s._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Random

@Slow
class WriterSpec extends AnyFlatSpec with Matchers {

  implicit val testServer = new Server {
    override val credentials = Credentials("PlotlyImageTest", "786r5mecv0")
    override val url = "https://api.plot.ly/v2/"
    override val readTimeoutMs = 60000
  }
  val testX1 = Vector(1.0, 2.0, 3.0)
  val testX2 = Vector(1, 2, 3)
  val testY1 = Vector(4.0, 5.0, 7.0)
  val testY2 = Vector(5, 10)
  val testZ1 = Vector(3.0, 1.0, 4.0)
  val testText1 = Vector("A", "B", "C")
  val testZData = Vector(Vector(1.0, 2.0, 3.0), Vector(1.0, 4.0, 3.0))

  def randomFileName: String = {
    val randomString = Random.alphanumeric.take(10).mkString("")
    s"test-scala-$randomString"
  }

  def checkTestX1(arr: JValue) = {
    val JArray(response) = arr
    response.toVector shouldEqual testX1.map { JDouble }
  }

  def checkTestX2(arr: JValue) = {
    val JArray(response) = arr
    response.toVector shouldEqual testX2.map { JInt(_) }
  }

  def checkTestY1(arr: JValue) = {
    val JArray(response) = arr
    response.toVector shouldEqual testY1.map { JDouble }
  }

  def checkTestZ1(arr: JValue) = {
    val JArray(response) = arr
    response.toVector shouldEqual testZ1.map { JDouble }
  }

  def checkTestY2(arr: JValue) = {
    val JArray(response) = arr
    response.toVector shouldEqual testY2.map { JInt(_) }
  }

  def checkTestText1(arr: JValue) = {
    val JArray(response) = arr
    response.toVector shouldEqual testText1.map { JString }
  }

  def checkTestZData(arr: JValue) = {
    val JArray(response) = arr
    val responseVector = response.toVector
    val rows = response.toVector.map {
      case JArray(row) => row.toVector
      case _ => fail("Not a 2D array")
    }
    rows(0) shouldEqual Vector(1.0, 2.0, 3.0).map { JDouble }
    rows(1) shouldEqual Vector(1.0, 4.0, 3.0).map { JDouble }
  }

  def getJsonForPlotFile(plotFile: PlotFile): JValue = {
    val endPoint = s"plots/${plotFile.fileId}/content"
    val req = Api.get(endPoint, Seq("inline_data" -> "true"))
    Api.despatchAndInterpret(req)
  }

  "draw" should "draw a basic scatter plot" in {
    val p = Plot().withScatter(testX1, testY1)
    val plotFile = draw(p, randomFileName)

    // Verify that the plot is correct
    val jsonResponse = getJsonForPlotFile(plotFile)
    val series = (jsonResponse \ "data")(0)
    checkTestX1(series \ "x")
    checkTestY1(series \ "y")
  }

  it should "draw a scatter plot with mixed Int/Double" in {
    val p = Plot().withScatter(testX2, testY1)
    val plotFile = draw(p, randomFileName)

    val jsonResponse = getJsonForPlotFile(plotFile)
    val series = (jsonResponse \ "data")(0)
    checkTestX2(series \ "x")
    checkTestY1(series \ "y")
  }

  it should "draw a scatter plot with set mode" in {
    val options0 = ScatterOptions().mode(ScatterMode.Line)
    val options1 = ScatterOptions()
      .mode(List(ScatterMode.Marker, ScatterMode.Line))
    val p = Plot()
      .withScatter(testX1, testY1, options0)
      .withScatter(testX1, testY1, options1)
    val plotFile = draw(p, randomFileName)

    val jsonResponse = getJsonForPlotFile(plotFile)
    val series0 = (jsonResponse \ "data")(0)
    val JString(mode0) = series0 \ "mode"
    mode0 shouldEqual "lines"
    val series1 = (jsonResponse \ "data")(1)
    val JString(mode1) = series1 \ "mode"
    mode1 should (equal("lines+markers") or equal("markers+lines"))
  }

  it should "draw a scatter plot with marker options" in {
    val options0 = ScatterOptions().marker(
      MarkerOptions()
        .size(22)
        .color(5, 10, 15, 0.5)
        .lineWidth(6)
        .lineColor(1, 2, 3, 0.1)
        .symbol("circle")
    )
    val p = Plot().withScatter(testX1, testY1, options0)
    val plotFile = draw(p, randomFileName)

    val jsonResponse = getJsonForPlotFile(plotFile)
    val series0 = (jsonResponse \ "data")(0)
    val marker = (series0 \ "marker")
    val JString(color) = (marker \ "color")
    color.replace(" ", "") shouldEqual "rgba(5,10,15,0.5)"
    (marker \ "size") shouldEqual JInt(22)
    (marker \ "line" \ "width") shouldEqual JInt(6)
    (marker \ "symbol") shouldEqual JString("circle")
    val JString(lineColor) = (marker \ "line" \ "color")
    lineColor.replace(" ", "") shouldEqual "rgba(1,2,3,0.1)"
  }

  it should "draw a scatter plot with text options" in {
    val options0 = ScatterOptions().text(testText1)
    val options1 = ScatterOptions().text("hello")

    val p = Plot()
      .withScatter(testX1, testY1, options0)
      .withScatter(testX1, testY1, options1)

    val plotFile = draw(p, randomFileName)
    val jsonResponse = getJsonForPlotFile(plotFile)
    val series0 = (jsonResponse \ "data")(0)
    checkTestText1(series0 \ "text")
    val series1 = (jsonResponse \ "data")(1)
    (series1 \ "text") shouldEqual JString("hello")
  }

  it should "draw a 3D plot" in {
    val p = ThreeDPlot()
      .withSurface(testZData)
    val plotFile = draw(p, randomFileName)
    val jsonResponse = getJsonForPlotFile(plotFile)
    val series0 = (jsonResponse \ "data")(0)
    checkTestZData(series0 \ "z")
    series0 \ "type" shouldEqual JString("surface")
  }

  it should "draw a 3D plot with x, y and z" in {
    val p = ThreeDPlot()
      .withSurface(testX1, testY2, testZData)
    val plotFile = draw(p, randomFileName)
    val jsonResponse = getJsonForPlotFile(plotFile)
    val series0 = (jsonResponse \ "data")(0)
    checkTestZData(series0 \ "z")
    checkTestX1(series0 \ "x")
    checkTestY2(series0 \ "y")
  }


  it should "draw a 3D scatter plot" in {
    val p = ThreeDPlot()
      .withScatter(testX1, testY1, testZ1)
    val plotFile = draw(p, randomFileName)
    val jsonResponse = getJsonForPlotFile(plotFile)
    val series0 = (jsonResponse \ "data")(0)
    series0 \ "type" shouldEqual JString("scatter3d")
    checkTestX1(series0 \ "x")
    checkTestY1(series0 \ "y")
    checkTestZ1(series0 \ "z")
  }
}
