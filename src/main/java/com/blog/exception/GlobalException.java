package com.blog.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> ResourceNotFoundExceptionHandler(ResourceNotFoundException rnfe, WebRequest req){
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setError(rnfe.getMessage());
		errorDetails.setDetails(req.getDescription(false));
		errorDetails.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		
	}

}
