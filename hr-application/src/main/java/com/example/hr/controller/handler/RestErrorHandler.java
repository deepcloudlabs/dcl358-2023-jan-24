package com.example.hr.controller.handler;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hr.domain.exception.EmployeeNotFoundException;
import com.example.hr.dto.error.ErrorMessage;

@RestControllerAdvice
public class RestErrorHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
		return e.getConstraintViolations()
				.stream()
				.map( cv -> new ErrorMessage(cv.getMessage(),"Validation Error"))
				.toList();                         
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return e.getAllErrors()
				.stream()
				.map( err -> new ErrorMessage(err.getDefaultMessage(),"Validation Error"))
				.toList();                         
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleException(Exception e) {
		return new ErrorMessage(e.getMessage(),"Bad Request");                         
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		return new ErrorMessage(e.getMessage(),"EmployeeNotFoundException");                         
	}
}
