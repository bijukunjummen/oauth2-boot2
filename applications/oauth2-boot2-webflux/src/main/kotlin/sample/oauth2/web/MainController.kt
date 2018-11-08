package sample.oauth2.web

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono
import sample.oauth2.service.TokenBeautifier

@Controller
class MainController(val tokenBeautifier: TokenBeautifier, val authorizedClientService: ReactiveOAuth2AuthorizedClientService) {

    @RequestMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    @RequestMapping("/secured/userinfo")
    fun userinfo(model: Model, authentication: OAuth2AuthenticationToken): Mono<String> {
        val oidcUser = authentication.principal as OidcUser

        val authorizedClient: Mono<OAuth2AuthorizedClient> = getAuthorizedClient(authentication)
        return authorizedClient.map { client ->
            val accessToken = client.accessToken.tokenValue
            model.addAttribute("raw_access_token", accessToken)
            model.addAttribute("formatted_access_token", tokenBeautifier.formatJwtToken(accessToken))

            val idToken = oidcUser.idToken.tokenValue
            model.addAttribute("raw_id_token", idToken)
            model.addAttribute("formatted_id_token", tokenBeautifier.formatJwtToken(idToken))

            model.addAttribute("userAttributes", oidcUser.claims)
            "userinfo"
        }

    }

    private fun getAuthorizedClient(authentication: OAuth2AuthenticationToken): Mono<OAuth2AuthorizedClient> {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.authorizedClientRegistrationId, authentication.name)
    }


}
