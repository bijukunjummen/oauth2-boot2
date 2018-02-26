package sample.oauth2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OAuth2LoginApplication

fun main(args: Array<String>) {
    runApplication<OAuth2LoginApplication>(*args)
}
