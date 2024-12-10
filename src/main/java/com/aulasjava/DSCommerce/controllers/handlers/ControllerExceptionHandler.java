package com.aulasjava.DSCommerce.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aulasjava.DSCommerce.DTO.CustomError;
import com.aulasjava.DSCommerce.DTO.ValidationError;
import com.aulasjava.DSCommerce.exceptions.DbException;
import com.aulasjava.DSCommerce.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> resourceNotFoundException(ResourceNotFoundException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(DbException.class)
	public ResponseEntity<CustomError> dbException(DbException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError(Instant.now(), status.value(), "Erro ao validar campo(s)!",
				request.getRequestURI());
		for (FieldError err : e.getFieldErrors()) {
			error.addError(err.getField(), err.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(error);
	}
}
