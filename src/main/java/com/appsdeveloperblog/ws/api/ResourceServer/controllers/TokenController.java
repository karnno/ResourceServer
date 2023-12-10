package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

	/**
	 * The @AuthenticationPrincipal annotation will bind the details of the currently authenticated principal
	 * into a special JWT object.
	 * From this object, we can extract the access token, the claims, etc...
	 * @param jwt
	 * @return
	 */
	@GetMapping
	public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt ) {
		return Collections.singletonMap("principal", jwt);
	}
}
