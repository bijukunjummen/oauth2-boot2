package sample.oauth2.web

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import sample.oauth2.service.TokenBeautifier

@Controller
class MainController(val tokenBeautifier: TokenBeautifier, val authorizedClientService: OAuth2AuthorizedClientService) {

    @RequestMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    @RequestMapping("/secured/userinfo")
    fun userinfo(model: Model, authentication: OAuth2AuthenticationToken): String {
        val oidcUser = authentication.principal as OidcUser
 
        val authorizedClient = getAuthorizedClient(authentication)
        val accessToken = authorizedClient.accessToken.tokenValue
        model.addAttribute("raw_access_token", accessToken)
        model.addAttribute("formatted_access_token", tokenBeautifier.formatJwtToken(accessToken))

        val idToken = oidcUser.idToken.tokenValue
        model.addAttribute("raw_id_token", idToken)
        model.addAttribute("formatted_id_token", tokenBeautifier.formatJwtToken(idToken))
        
        model.addAttribute("userAttributes", oidcUser.claims)
        return "userinfo"
    }

    private fun getAuthorizedClient(authentication: OAuth2AuthenticationToken): OAuth2AuthorizedClient {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.authorizedClientRegistrationId, authentication.name)
    }


}
