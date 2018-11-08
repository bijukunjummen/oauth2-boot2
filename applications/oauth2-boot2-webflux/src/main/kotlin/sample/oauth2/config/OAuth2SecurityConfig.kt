package sample.oauth2.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class OAuth2SecurityConfig() {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.csrf().disable()
//        @formatter:off
        http
            .authorizeExchange()
                .pathMatchers("/secured/**")
                    .authenticated()
                .pathMatchers("/", "/custom_login", "/favicon.ico", "/webjars/**", "/css/**")
                    .permitAll()
                .anyExchange()
                    .authenticated()
                    .and()
                .oauth2Login()
//        @formatter:on

        return http.build()

    }

//    fun configure(web: WebSecurity) {
//        super.configure(web)
//        web.ignoring()
//                .mvcMatchers(
//                        "/favicon.ico",
//                        "/webjars/**",
//                        "/css/**"
//                )
//    }

//    fun configure(http: HttpSecurity) {
//        http.csrf().disable()
////        @formatter:off
//        http.authorizeRequests()
//                .antMatchers("/secured/**")
//                .authenticated()
//                .antMatchers("/", "/custom_login")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2Login()
//                .loginPage("/custom_login")
////        @formatter:on
//    }
}