package sample.oauth2.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.nimbusds.jose.util.Base64
import org.springframework.stereotype.Service

@Service
class TokenBeautifier(val objectMapper: ObjectMapper) {

    fun formatJwtToken(token: String): String {
        return toPrettyJsonString(parseToken(token))
    }

    private fun parseToken(base64Token: String): Map<String, *> {
        val tokens = base64Token.split(".")
        val token = tokens[1]
        return this.objectMapper.readValue(Base64(token).decodeToString())
    }

    private fun toPrettyJsonString(obj: Any): String {
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
    }
}
