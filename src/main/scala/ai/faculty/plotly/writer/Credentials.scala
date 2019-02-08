package ai.faculty.plotly.writer

import scala.io.Source

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

case class Credentials(username: String, key: String)

object Credentials {

  implicit val formats = DefaultFormats

  val credentialsPath = sys.env("HOME") + "/.plotly/.credentials"

  def read: Credentials = fromFile(credentialsPath)

  def fromFile(fileName: String): Credentials = {
    val fileContents = Source.fromFile(fileName).mkString
    fromString(fileContents)
  }

  def fromString(credentialsString: String): Credentials = {
    val credentialsJson = parse(credentialsString)
    val credentials = credentialsJson.transformField {
      case ("api_key", key) => ("key", key)
    }.extract[Credentials]
    credentials
  }

}
