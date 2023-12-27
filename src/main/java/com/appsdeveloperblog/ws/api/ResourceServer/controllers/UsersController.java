package com.appsdeveloperblog.ws.api.ResourceServer.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.ws.api.ResourceServer.response.UserRest;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status(){
        return "Working";
    }
    
    //		@PreAuthorize(value = "hasRole('developer')")    // PreAuthorize defines whether a method can be invoked or not. 
	//      @PreAuthorize(value = "hasAuthority('ROLE_developer') or #id == #jwt.subject")  // Notice we use the prefix ROLE_
    // 		@PostAuthorize(value = "")   	// PostAuthorize is called AFTER the method is called
    //		@Secured("ROLE_developer") 		// the role as it appears in the JWT token
    @PreAuthorize("#id == #jwt.subject")
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    	return "Delete user with id "+ id  + " and JWT subject " + jwt.getSubject();
    }
    
    
    @PostAuthorize("returnObject.userId == #jwt.subject")  // returnObject is a reserved term
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    	return new UserRest("Karnno", "Nov", "686c79af-64b7-4dfd-99ba-9fc26f05096a");
    }
}
