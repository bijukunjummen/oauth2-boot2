package sample.oauth2

import org.hamcrest.CoreMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class HomePageSecurityTests {
    
    @Autowired
    lateinit var mockMvc: MockMvc
    
    @Test
    fun `calling root uri should retrieve index page without needing security`() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("OAuth 2.0 Login with Spring Security")))
    }
    
    @Test
    fun `calling a secured page should redirect to sso via custom login page`() {
        mockMvc.perform(get("/secured/someurl"))
                .andExpect(status().is3xxRedirection)
                .andExpect(header().string("Location", containsString("/custom_login")))
    }

    @Test
    fun `calling the custom_login uri should redirect to the spring security version of uri`() {
        mockMvc.perform(get("/custom_login"))
                .andExpect(status().is3xxRedirection)
                .andExpect(header().string("Location", containsString("/oauth2/authorization/uaa")))
    }

    @Test
    fun `calling the spring security version of uri should redirect to UAA`() {
        mockMvc.perform(get("/oauth2/authorization/uaa"))
                .andExpect(status().is3xxRedirection)
                .andExpect(header().string("Location", allOf(
                        containsString("/uaa/oauth/authorize"), containsString("response_type=code"))))
    }
    
}