package ai.faculty.plotly

case class Margins(
  top: Option[Int] = None,
  right: Option[Int] = None,
  bottom: Option[Int] = None,
  left: Option[Int] = None
)

object emptyMargins {
  def unapply(margin: Margins): Boolean = margin == Margins()
}
