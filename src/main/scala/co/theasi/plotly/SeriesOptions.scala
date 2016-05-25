package co.theasi.plotly

trait SeriesOptions {
  val name: Option[String]
}

case class BoxOptions(
  name: Option[String] = None
) extends SeriesOptions {
  def name(newName: String): BoxOptions = copy(name = Some(newName))
}


case class BarOptions(
  name: Option[String] = None
) extends SeriesOptions {
  def name(newName: String): BarOptions = copy(name = Some(newName))
}
