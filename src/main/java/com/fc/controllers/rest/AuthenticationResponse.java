package com.fc.controllers.rest;

public class AuthenticationResponse {

	private final String jwt;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getjwt() {
		return jwt;
	}	
	
}
