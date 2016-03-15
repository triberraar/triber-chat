package be.tribersoft.triber.chat.configuration;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import be.tribersoft.triber.chat.security.StatelessAuthenticationFilter;
import be.tribersoft.triber.chat.security.StatelessLoginFilter;
import be.tribersoft.triber.chat.security.TokenAuthenticationService;
import be.tribersoft.triber.chat.security.UserDetailsService;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	private UserDetailsService userDetailsService;
	@Inject
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			
			.authorizeRequests()
				.antMatchers("/js/**", "/lib/**", "/lib-static/**", "/index.html", "/chat.html", "/").permitAll()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers(HttpMethod.PUT, "/user/{userId}/activate").permitAll()
				.antMatchers(HttpMethod.POST, "/reset-password").permitAll()
				.antMatchers(HttpMethod.POST, "/reset-password/{resetPasswordId}").permitAll()
				.antMatchers(HttpMethod.GET, "/chat/**").authenticated()
				.anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new StatelessLoginFilter("/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
		
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
