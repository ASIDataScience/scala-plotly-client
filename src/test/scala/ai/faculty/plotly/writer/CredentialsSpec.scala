package ai.faculty.plotly.writer

import org.scalatest._

class CredentialsSpec extends FlatSpec with Matchers {
  "fromString" should "read the credentials from a string" in {
    val username = "test-user"
    val key = "test-key"
    val s = s"""{ "username": "$username", "key": "$key" }"""
    Credentials.fromString(s) shouldEqual Credentials(username, key)
  }
}
