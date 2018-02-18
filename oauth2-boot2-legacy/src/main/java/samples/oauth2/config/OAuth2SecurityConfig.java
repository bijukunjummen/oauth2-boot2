package samples.oauth2.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);

        //@formatter:off
		web.ignoring()
                .mvcMatchers("/favicon.ico", "/webjars/**", "/css/**");
		//@formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		//@formatter:off
        http.authorizeRequests()
                .antMatchers("/secured/**")
                    .authenticated()
                .antMatchers("/")
                    .permitAll()
                .anyRequest()
                    .authenticated();
        //@formatter:on
	}

}
