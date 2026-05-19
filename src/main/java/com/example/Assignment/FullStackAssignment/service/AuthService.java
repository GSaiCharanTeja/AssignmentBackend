package com.example.Assignment.FullStackAssignment.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Assignment.FullStackAssignment.dto.AuthResponse;
import com.example.Assignment.FullStackAssignment.dto.LoginRequest;
import com.example.Assignment.FullStackAssignment.dto.RegisterRequest;
import com.example.Assignment.FullStackAssignment.entity.User;
import com.example.Assignment.FullStackAssignment.repository.UserRepository;
import com.example.Assignment.FullStackAssignment.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository up;
    private final PasswordEncoder pe;
    private final JwtUtil jw;

    public AuthService(
            UserRepository up,
            PasswordEncoder pe,
            JwtUtil jw) {

        this.up = up;
        this.pe = pe;
        this.jw = jw;
    }

    // REGISTER
    public String register(RegisterRequest req) {

        Optional<User> eu =
                up.findByEmail(req.getEmail());

        if (eu.isPresent()) {

            return "Email already Exist";
        }

        User user = new User();

        user.setName(req.getName());

        user.setEmail(req.getEmail());

        // Encode Password
        user.setPassword(
                pe.encode(req.getPassword())
        );

        // Role
        user.setRole("USER");

        up.save(user);

        return "User Registered Successfully";
    }

    // LOGIN
    public AuthResponse login(LoginRequest req) {

        Optional<User> uo =
                up.findByEmail(req.getEmail());

        if (uo.isEmpty()) {

            return new AuthResponse(
                    null,
                    "Invalid email"
            );
        }

        User user = uo.get();

        boolean isPasswordCorrect =
                pe.matches(
                        req.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordCorrect) {

            return new AuthResponse(
                    null,
                    "Invalid password"
            );
        }

        String token =
                jw.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Login Successful"
        );
    }
}