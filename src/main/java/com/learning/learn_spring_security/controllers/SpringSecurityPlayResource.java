package com.learning.learn_spring_security.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class SpringSecurityPlayResource {
	
	@GetMapping("/csrf-token")
	public CsrfToken retrieveCsrfToken(HttpServletRequest httpServletRequest) {
		System.out.println(httpServletRequest.getAttribute("_csrf"));
		return (CsrfToken) httpServletRequest.getAttribute("_csrf");
	}

}
