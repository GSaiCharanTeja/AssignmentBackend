package com.example.Assignment.FullStackAssignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Assignment.FullStackAssignment.dto.AuthResponse;
import com.example.Assignment.FullStackAssignment.dto.LoginRequest;
import com.example.Assignment.FullStackAssignment.dto.RegisterRequest;
import com.example.Assignment.FullStackAssignment.service.AuthService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/auth")
public class AuthController {
	private final AuthService as;
	
	public AuthController(AuthService as) {
		this.as=as;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest req){
		String res=as.register(req);
		
		return new ResponseEntity<>(
				res,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse>login(@RequestBody LoginRequest req){
		AuthResponse res=as.login(req);
		
		return new ResponseEntity<>(
				res,HttpStatus.OK);
	}
}
