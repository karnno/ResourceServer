package com.appsdeveloperblog.ws.api.ResourceServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	@Bean
	DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
	
		// allows us to define authorization rules as a parameter
		http.authorizeHttpRequests( 
				authz -> 
					authz
					.antMatchers(HttpMethod.GET, "/users/status/check").hasAuthority("SCOPE_profile")// extra filter on the user's profile
					.anyRequest()
					.authenticated() // ensures that URLs are allowed by any AUTHENTICATED user !
				)
			.oauth2ResourceServer( 					// setting our application to function as Oauth2 resource server
				oauth2 ->
					oauth2.jwt(jwt->{
						
					})								//and it means that our application will expect incoming requests 
													//to have special kind of token known as JWT (Json Web Token), and it will
													// be included as part of their security credentials
				)
		; 
		
		/* This is another way to write it.
		http
		.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/users/status/check")
				.hasAuthority("SCOPE_profile")
			.anyRequest().authenticated()
			.and()
		.oauth2ResourceServer()
		.jwt();
		*/
		
		return http.build();						// builds HTTP security instance into a security filter chain.
	}

}
