package ai.faculty.plotly

import org.scalatest._

class SurfaceOptionsSpec extends FlatSpec with Matchers {

  "SurfaceOptions" should "support setting opacity" in {
    val opts0 = SurfaceOptions().opacity(0.4)
    opts0.opacity shouldEqual(Some(0.4))
  }

  it should "support setting whether to show the scale" in {
    val opts = SurfaceOptions()
    opts.withScale().showScale shouldEqual Some(true)
    opts.noScale().showScale shouldEqual Some(false)
  }

  it should "support setting the colorscale to a predefined scale" in {
    val opts = SurfaceOptions()
    opts.colorscale("Viridis").colorscale shouldEqual Some(
      ColorscalePredef("Viridis"))
  }

}
