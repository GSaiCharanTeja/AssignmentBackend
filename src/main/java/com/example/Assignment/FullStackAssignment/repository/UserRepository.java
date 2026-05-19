package com.example.Assignment.FullStackAssignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.example.Assignment.FullStackAssignment.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
}
