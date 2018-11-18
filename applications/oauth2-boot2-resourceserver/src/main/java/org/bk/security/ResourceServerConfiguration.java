package org.bk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfiguration {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeExchange()
                .pathMatchers("/secured/read")
                    .hasAuthority("SCOPE_resource.read")
                .pathMatchers("/secured/write")
                    .hasAuthority("SCOPE_resource.write")
                .anyExchange().authenticated()
                    .and()
                .oauth2ResourceServer()
                    .jwt();

        return http.build();
        // @formatter:on
    }


}
