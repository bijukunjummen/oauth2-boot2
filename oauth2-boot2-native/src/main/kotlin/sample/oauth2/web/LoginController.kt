package sample.oauth2.web


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class LoginController {

    @RequestMapping("/custom_login")
    fun loginPage(): String {
        return "redirect:/oauth2/authorization/uaa"
    }
}
