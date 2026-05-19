package com.example.Assignment.FullStackAssignment.service;

import java.util.Collections;

import org.springframework.security.core.authority.
SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.
UserDetails;

import org.springframework.security.core.userdetails.
UserDetailsService;

import org.springframework.security.core.userdetails.
UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.Assignment.FullStackAssignment.entity.User;
import com.example.Assignment.FullStackAssignment.repository.UserRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository ur;

    public CustomUserDetailsService(
            UserRepository ur) {

        this.ur = ur;
    }

    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        User user =
                ur.findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found"));

        return new org.springframework.security.core.userdetails.User(

                user.getEmail(),

                user.getPassword(),

                Collections.singletonList(

                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                )
        );
    }
}