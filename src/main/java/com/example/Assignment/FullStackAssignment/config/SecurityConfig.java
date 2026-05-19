package com.example.Assignment.FullStackAssignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Assignment.FullStackAssignment.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jaf;

    public SecurityConfig(JwtAuthFilter jaf) {
        this.jaf = jaf;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )
            .addFilterBefore(
                    jaf,
                    UsernamePasswordAuthenticationFilter.class
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/v1/auth/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()
                    .requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**"
                    ).permitAll()
                    .anyRequest()
                    .authenticated()
            );

        return http.build();
    }
}