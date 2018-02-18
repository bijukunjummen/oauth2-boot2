package sample.oauth2.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class OAuth2SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(web: WebSecurity) {
        super.configure(web)
        web.ignoring()
                .mvcMatchers(
                        "/favicon.ico",
                        "/webjars/**",
                        "/css/**"
                )
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
//        @formatter:off
        http.authorizeRequests()
                .antMatchers("/secured/**")
                    .authenticated()
                .antMatchers("/", "/custom_login")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                    .oauth2Login()
                    .loginPage("/custom_login")
//        @formatter:on        
    }
}