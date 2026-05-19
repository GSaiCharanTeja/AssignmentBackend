package com.example.Assignment.FullStackAssignment.security;

import java.io.IOException;

import org.springframework.security.authentication.
UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.
SecurityContextHolder;

import org.springframework.security.core.userdetails.
UserDetails;

import org.springframework.security.core.userdetails.
UserDetailsService;

import org.springframework.security.web.authentication.
WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.
OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil ju;
    private final UserDetailsService uds;

    public JwtAuthFilter(
            JwtUtil ju,
            UserDetailsService uds) {

        this.ju = ju;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip Auth APIs
        if (path.startsWith("/api/v1/auth")) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String email = null;

        // Extract Token
        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            try {

                email = ju.extractEmail(token);

            } catch (Exception e) {

                System.out.println("Invalid JWT Token");

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }
        }

        // Validate Token
        if (email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails ud =
                    uds.loadUserByUsername(email);

            if (ju.validateToken(
                    token,
                    ud.getUsername()
            )) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                ud,
                                null,
                                ud.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}