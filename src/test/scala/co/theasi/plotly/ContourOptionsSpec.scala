package co.theasi.plotly

import org.scalatest._

class ContourOptionsSpec extends FlatSpec with Matchers {

  "ContourOptions" should "support setting the colorscale to a preset" in {
    val opts = ContourOptions().colorscale("Viridis")
    opts.colorscale shouldEqual Some(ColorscalePredef("Viridis"))
  }

}
