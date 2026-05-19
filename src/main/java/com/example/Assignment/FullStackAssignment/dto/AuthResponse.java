package com.example.Assignment.FullStackAssignment.dto;

public class AuthResponse {
	private String token;
	private String message;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "AuthResponse [token=" + token + ", message=" + message + "]";
	}
	public AuthResponse(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
