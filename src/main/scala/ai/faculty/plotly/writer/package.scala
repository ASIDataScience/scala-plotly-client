package ai.faculty.plotly

package object writer {
  case class PlotlyException(message: String)
    extends Exception(message)

  case class UnexpectedServerResponse(message: String)
    extends Exception(message)
}
