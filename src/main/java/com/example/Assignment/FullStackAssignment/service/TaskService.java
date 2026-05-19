package com.example.Assignment.FullStackAssignment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Assignment.FullStackAssignment.dto.TaskRequest;
import com.example.Assignment.FullStackAssignment.entity.Task;
import com.example.Assignment.FullStackAssignment.entity.User;
import com.example.Assignment.FullStackAssignment.repository.TaskRepository;
import com.example.Assignment.FullStackAssignment.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository tr;
    private final UserRepository ur;

    public TaskService(TaskRepository tr,
                       UserRepository ur) {

        this.tr = tr;
        this.ur = ur;
    }

    // CREATE TASK
    public String createTask(TaskRequest req) {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user =
                ur.findByEmail(email)
                        .orElseThrow();

        Task task = new Task();

        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setStatus(req.getStatus());
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        tr.save(task);

        return "Task Created Successfully";
    }
    // GET USER TASKS
    public List<Task> getMyTasks() {

        String email =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        User user =
                ur.findByEmail(email)
                        .orElseThrow();

        return tr.findByUserId(user.getId());
    }
    
    
    public String updateTask(int id,TaskRequest req) {
    	Task task=tr.findById(id).orElseThrow(()->new RuntimeException("Task Not Found"));
    	
    	task.setTitle(req.getTitle());
    	task.setDescription(req.getDescription());
    	task.setStatus(req.getStatus());
    	tr.save(task);
    	return "Task Updated Successfully";
    }
    
    public String deletTask(int id) {
    	Task task=tr.findById(id).orElseThrow(()->new RuntimeException("Task Not Found"));
    	
    	tr.delete(task);
    	
    	return "task Deleted Succesfully";
    }
}