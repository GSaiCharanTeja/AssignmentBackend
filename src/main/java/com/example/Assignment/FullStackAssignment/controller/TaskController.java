package com.example.Assignment.FullStackAssignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Assignment.FullStackAssignment.dto.TaskRequest;
import com.example.Assignment.FullStackAssignment.entity.Task;
import com.example.Assignment.FullStackAssignment.service.TaskService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService ts;

    public TaskController(TaskService ts) {
        this.ts = ts;
    }
    @PostMapping
    public ResponseEntity<String> createTask(
            @RequestBody TaskRequest req) {

        String response = ts.createTask(req);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
    @GetMapping
    public ResponseEntity<List<Task>> getMyTasks() {

        List<Task> tasks = ts.getMyTasks();

        return new ResponseEntity<>(
                tasks,
                HttpStatus.OK
        );
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id,@RequestBody TaskRequest req){
    	String res=ts.updateTask(id,req);
    	return new ResponseEntity<>(
    			res,HttpStatus.OK);
    			
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id){
    	String res=ts.deletTask(id);
    	return new ResponseEntity<>(
    			res,HttpStatus.OK);
    }
}