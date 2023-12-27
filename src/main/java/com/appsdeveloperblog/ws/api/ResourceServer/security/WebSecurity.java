package com.appsdeveloperblog.ws.api.ResourceServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * migrate from the deprecated WebSecurityConfigurerAdapter towards the content-based security configuration: 
 * https://www.appsdeveloperblog.com/migrating-from-deprecated-websecurityconfigureradapter/
 */
// @org.springframework.context.annotation.Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // we can use the @Secured annotation, @PreAuthorized and @PostAuthorized
@EnableWebSecurity
public class WebSecurity {
	
	@Bean
	DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
	
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter()) ;
		// allows us to define authorization rules as a parameter
		http.authorizeHttpRequests( 
				authz -> 
					authz
					.antMatchers(HttpMethod.GET, "/users/status/check")
					//hasAuthority("SCOPE_profile")	// extra filter on the user's profile
					.hasRole   ("developer")  			
					//.hasAnyRole("developer", "end_user")
					//.hasAuthority("ROLE_developer") // notice the difference between hasAuthority that needs prefix SCOPE_ and hasRole that does not need a prefix
					.anyRequest().authenticated() 				// ensures that URLs are allowed by any AUTHENTICATED user !
				)
			.oauth2ResourceServer( 					// setting our application to function as Oauth2 resource server
				oauth2 ->
					oauth2.jwt(jwt->{				//and it means that our application will expect incoming requests to have special kind of token known as JWT (Json Web Token), and it will be included as part of their security credentials
						jwt.jwtAuthenticationConverter(converter); // we expect incoming requests to have a JWT and we set the converter that will read the roles.
					})								
				)
		; 

		// when filtering by roles, Spring boot must look INSIDE the access token. 
		// Spring boot offers us to create a Converter Class that reads the list of roles and converts each role into a granted authority
		
		
		
		
		
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
