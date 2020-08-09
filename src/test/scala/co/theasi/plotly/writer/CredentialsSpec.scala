package co.theasi.plotly.writer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CredentialsSpec extends AnyFlatSpec with Matchers {
  "fromString" should "read the credentials from a string" in {
    val username = "test-user"
    val key = "test-key"
    val s = s"""{ "username": "$username", "key": "$key" }"""
    Credentials.fromString(s) shouldEqual Credentials(username, key)
  }
}
