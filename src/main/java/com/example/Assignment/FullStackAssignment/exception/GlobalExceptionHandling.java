package com.example.Assignment.FullStackAssignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandling {
	
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException exp){
		Map<String,Object> err=new HashMap<>();
		
		err.put("timestamp",LocalDateTime.now());
		err.put("message", exp.getMessage());
		err.put("status",HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(
				err,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,Object>> handleException(Exception exp){
		Map<String,Object> er=new HashMap<>();
		er.put("timestamp",LocalDateTime.now());
		er.put("message",exp.getMessage());
		er.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		return new ResponseEntity<>(
				er,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
