package sample.oauth2

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
class HomePageSecurityTests {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `calling root uri should retrieve index page without needing security`() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { resp -> Assertions.assertThat(String(resp.responseBody!!)).contains("Sample OAuth2") }
    }

    @Test
    fun `calling a secured page should redirect to sso via custom login page`() {
        webTestClient.get()
                .uri("/secured/someurl")
                .exchange()
                .expectStatus().is3xxRedirection
                .expectHeader()
                .valueMatches("Location", "/oauth2/authorization/uaa")

    }

    @Test
    fun `calling the custom_login uri should redirect to the spring security version of uri`() {
        webTestClient.get()
                .uri("/custom_login")
                .exchange()
                .expectStatus().is3xxRedirection
                .expectHeader()
                .valueMatches("Location", "/oauth2/authorization/uaa")
    }

    @Test
    fun `calling the spring security version of uri should redirect to UAA`() {
        webTestClient.get()
                .uri("/oauth2/authorization/uaa")
                .exchange()
                .expectStatus().is3xxRedirection
                .expectHeader()
                .valueMatches("Location", ".*?/uaa/oauth/authorize.response_type=code.*")

    }

}