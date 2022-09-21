package com.joel.catalog.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResouceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		var error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		var error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Database exception !");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> database(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		
		var error = new ValidationError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Validation exception !");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		for(FieldError fielError : e.getBindingResult().getFieldErrors()) {
			error.addError(fielError.getField(), fielError.getDefaultMessage());
		};
		
		return ResponseEntity.status(status).body(error);
	}

}
