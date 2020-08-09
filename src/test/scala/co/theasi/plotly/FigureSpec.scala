package co.theasi.plotly

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FigureSpec extends AnyFlatSpec with Matchers with Inside {

  def xSize(viewPort: ViewPort) =
    viewPort.xDomain._2 - viewPort.xDomain._1

  def ySize(viewPort: ViewPort) =
    viewPort.yDomain._2 - viewPort.yDomain._1

  "SinglePlotFigure" should "allow setting the legend" in {
    val f = SinglePlotFigure()
      .legend(LegendOptions().fontSize(20).x(0.5))
    f.options.legendOptions.x shouldEqual Some(0.5)
    f.options.legendOptions.font.size shouldEqual Some(20)
  }

  it should "allow setting margin options" in {
    val fo = SinglePlotFigure()
    val fo1 = fo.margins(1, 2, 3, 4)
    val fo2 = fo.topMargin(1).rightMargin(2).bottomMargin(3).leftMargin(4)
    fo1 shouldEqual fo2
  }

  it should "allow setting paper background color" in {
    val fo = SinglePlotFigure()
    val fo1 = fo.paperBackgroundColor(10, 20, 30)
    val fo2 = fo.paperBackgroundColor(Color.rgb(10, 20, 30))
    fo1 shouldEqual fo2
    fo.options.paperBackgroundColor shouldEqual None
  }

  it should "allow setting plot background color" in {
    val fo = SinglePlotFigure()
    val fo1 = fo.plotBackgroundColor(10, 20, 30)
    val fo2 = fo.plotBackgroundColor(Color.rgb(10, 20, 30))
    fo1 shouldEqual fo2
    fo.options.plotBackgroundColor shouldEqual None
  }

  "GridFigure" should "create subplots in a row" in {
    val f = GridFigure(1, 2)
    val hspace = GridFigure.DefaultHorizontalSpacing / 2.0
    val vspace = GridFigure.DefaultVerticalSpacing / 1.0
    f.viewPorts.size shouldEqual 2
    f.viewPorts(0).xDomain shouldEqual((0.0, 0.5 - 0.5*hspace))
    f.viewPorts(1).xDomain shouldEqual((0.5 + 0.5*hspace, 1.0))
    f.viewPorts.map { vp => vp.yDomain shouldEqual((0.0, 1.0)) }
  }

  it should "create subplots in a column" in {
    val f = GridFigure(2, 1)
    val hspace = GridFigure.DefaultHorizontalSpacing / 1.0
    val vspace = GridFigure.DefaultVerticalSpacing / 2.0
    f.viewPorts.size shouldEqual 2
    f.viewPorts.map { vp => vp.xDomain shouldEqual((0.0, 1.0)) }
    f.viewPorts(0).yDomain shouldEqual((0.5 + 0.5*vspace, 1.0))

    // FPE occurs for yAxes(1) -> compare using approximate equality
    inside(f.viewPorts(1).yDomain) { case (bottom, top) =>
      bottom shouldEqual 0.0 +- 1e-5
      top shouldEqual(0.5 - 0.5*vspace) +- 1e-5
    }
  }

  it should "create subplots in a grid" in {
    val f = GridFigure(4, 5) // 4 rows, 5 columns
    val hspace = GridFigure.DefaultHorizontalSpacing / 4.0
    val vspace = GridFigure.DefaultVerticalSpacing / 5.0

    f.viewPorts.size shouldEqual 20

    // Check xAxes span each row
    (0 until 4).foreach { irow =>
      f.viewPortAt(irow, 0).xDomain._1 shouldEqual 0.0
      f.viewPortAt(irow, 4).xDomain._2 shouldEqual 1.0
    }

    // Check yAxes span each column
    (0 until 5).foreach { icol =>
      f.viewPortAt(0, icol).yDomain._2 shouldEqual 1.0 +- 1e-5
      f.viewPortAt(3, icol).yDomain._1 shouldEqual 0.0 +- 1e-5
    }

    // Check xAxes all have same width
    val width = xSize(f.viewPorts(0))
    f.viewPorts.foreach { vp => xSize(vp) shouldEqual width +- 1e-5 }

    val height = ySize(f.viewPorts(0))
    f.viewPorts.foreach { vp => ySize(vp) shouldEqual height +- 1e-5 }

  }

  it should "allow plotting on each of the subplots" in {
    val fig = GridFigure(2, 3)
    val p00 = CartesianPlot().withScatter(List(1, 2), List(2, 3))
    val p01 = CartesianPlot().withScatter(List(2, 4), List(2, 3))
    val p12 = CartesianPlot().withScatter(List(2, 5), List(2, 3))
    val modifiedFig = fig.plot(0, 0)(p00).plot(0, 1)(p01).plot(1, 2)(p12)

    modifiedFig.plotAt(0, 0) shouldEqual p00
    modifiedFig.plotAt(0, 1) shouldEqual p01
    modifiedFig.plotAt(1, 2) shouldEqual p12
  }

  "RowFigure" should "put subplots in a row" in {
    val fig = RowFigure(3)
    fig.viewPorts.foreach { vp => vp.yDomain shouldEqual((0.0, 1.0)) }

    fig.viewPorts(0).xDomain._1 shouldEqual 0.0
    fig.viewPorts(2).xDomain._2 shouldEqual 1.0

    val width = xSize(fig.viewPorts(0))
    fig.viewPorts.foreach { vp => xSize(vp) shouldEqual width +- 1e-5 }
    fig.viewPorts.sliding(2).foreach {
      case Vector(vpLeft, vpRight) =>
        vpLeft.xDomain._2 should be < vpRight.xDomain._1
    }
  }

  it should "allow plotting on each of the subplots" in {
    val fig = RowFigure(3)
    val p0 = CartesianPlot().withScatter(List(1, 2), List(2, 3))
    val p1 = CartesianPlot().withScatter(List(2, 4), List(2, 3))
    val p2 = CartesianPlot().withScatter(List(2, 5), List(2, 3))
    val modifiedFig = fig.plot(0)(p0).plot(1)(p1).plot(2)(p2)

    modifiedFig.plots(0) shouldEqual p0
    modifiedFig.plots(1) shouldEqual p1
    modifiedFig.plots(2) shouldEqual p2
  }

}
