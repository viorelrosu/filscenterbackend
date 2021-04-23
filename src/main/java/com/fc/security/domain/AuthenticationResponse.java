package com.fc.security.domain;

public class AuthenticationResponse {

	private final String jwt;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getjwt() {
		return jwt;
	}	
	
}
