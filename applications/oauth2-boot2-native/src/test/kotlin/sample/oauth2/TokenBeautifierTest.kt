package sample.oauth2

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.boot.test.json.BasicJsonTester
import sample.oauth2.service.TokenBeautifier


class TokenBeautifierTest {

    private val jsonTester = BasicJsonTester(javaClass);
    @Test
    fun `should be able to beautify a jwt token`() {
        val t = TokenBeautifier(ObjectMapper())
        val formatted = t.formatJwtToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9." +
                "TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ")
     
        assertThat(jsonTester.from(formatted)).isEqualToJson("""
           | {
           |  "sub" : "1234567890",
           |  "name" : "John Doe",
           |  "admin" : true
           |  } 
        """.trimMargin())
    }
}