package com.cts.demo.feedbackservice.exceptions;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		// Get all errors
		ex.getBindingResult().getAllErrors().forEach(error -> {
				body.put(((FieldError)error).getField(),error.getDefaultMessage());
			});
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = FeedbackNotFound.class)
	public ResponseEntity<ExceptionResponse> handleAdminRegistrationException(FeedbackNotFound exception,
			WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception exception, WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

	}
}