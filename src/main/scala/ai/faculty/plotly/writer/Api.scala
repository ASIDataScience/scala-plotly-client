package ai.faculty.plotly.writer

import scalaj.http._

import org.json4s._
import org.json4s.native.JsonMethods._

object Api {

  def get(endPoint: String)(implicit server: Server): HttpRequest = {
    val request = Http(server.url + endPoint.stripPrefix("/"))
      .auth(server.credentials.username, server.credentials.key)
      .timeout(
        connTimeoutMs=server.connTimeoutMs,
        readTimeoutMs=server.readTimeoutMs)
      .headers(Seq(
        "Plotly-Client-Platform" -> "scala",
        "Accept" -> "application/json"
      ))
    request
  }

  def get(
      endPoint: String,
      params: Seq[(String, String)])
      (implicit server: Server)
  : HttpRequest = {
    get(endPoint).params(params)
  }

  def post(
      endPoint: String,
      body: String)
      (implicit server: Server)
  : HttpRequest = {
    val request = Http(server.url + endPoint.stripPrefix("/"))
      .auth(server.credentials.username, server.credentials.key)
      .headers(Seq(
        "Plotly-Client-Platform" -> "scala",
        "Accept" -> "application/json",
        "Content-Type" -> "application/json"
      ))
      .postData(body)
    request
  }

  def delete(endPoint: String)(implicit server: Server): HttpRequest = {
    get(endPoint).method("DELETE")
  }

  def despatchAndInterpret(
      request: HttpRequest)
      (implicit server: Server)
  : JValue = {
    val response = request.asString
    if (!response.is2xx) {
      val responseBody = parse(response.body)
      val JString(msg) = responseBody \ "detail"
      throw new PlotlyException(msg)
    }
    else {
      parse(response.body)
    }
  }

}
