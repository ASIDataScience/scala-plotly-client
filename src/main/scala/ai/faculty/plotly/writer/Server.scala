package ai.faculty.plotly.writer

case object ServerWithDefaultCredentials extends Server {
  override lazy val credentials = Credentials.read
  val url = "https://api.plot.ly/v2/"
}

trait Server {
  def credentials: Credentials
  def url: String
  // scalastyle:off magic.number
  def connTimeoutMs: Int = 2000
  def readTimeoutMs: Int = 10000
  // scalastyle:on magic.number
}
