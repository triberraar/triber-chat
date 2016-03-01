package be.tribersoft.triber.chat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			.formLogin()
				.loginPage("/index.html")
				.defaultSuccessUrl("/chat.html")
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/index.html")
				.permitAll()
				.and()
			.authorizeRequests()
				.antMatchers("/js/**", "/lib/**", "/index.html", "/").permitAll()
				.anyRequest().authenticated();
		// @formatter:on
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER").and()
			.withUser("user1").password("password").roles("USER").and()
			.withUser("admin").password("admin").roles("USER", "ADMIN", "READER", "WRITER");
		// @formatter:on
	}
}
