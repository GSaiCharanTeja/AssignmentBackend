package com.example.Assignment.FullStackAssignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Assignment.FullStackAssignment.entity.Task;

public interface TaskRepository
        extends JpaRepository<Task, Integer> {

    List<Task> findByUserId(int userId);
}