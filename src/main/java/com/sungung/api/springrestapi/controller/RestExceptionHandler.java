package com.sungung.api.springrestapi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.sungung.api.springrestapi.entity.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value= {ArithmeticException.class})
	protected ResponseEntity<Object> handleArithmeticException(RuntimeException ex, WebRequest request){
		return new ResponseEntity<Object>(new ErrorResponse("Cannot divided by zero"), new HttpHeaders(), HttpStatus.CONFLICT);
	}	
	
	@ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class, MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<ErrorResponse> handleIllegalVersionException(Exception ex, WebRequest request){
		// Return UNSUPPORTED_MEDIA_TYPE when only version is invalid.
		if ((ex instanceof MethodArgumentTypeMismatchException) && "apiVersion".equals(((MethodArgumentTypeMismatchException)ex).getName())) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse("Unsupported version"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		} else if (ex instanceof HttpMediaTypeNotSupportedException && ex.getMessage().contains("application/vnd.api")) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse("Unsupported version"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);			
		} else {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
		}
	}
		
}
