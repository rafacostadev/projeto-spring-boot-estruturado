package com.aulasjava.DSCommerce.DTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError {

	private List<FieldError> errors = new ArrayList<>();

	public ValidationError(Instant timeStamp, Integer status, String error, String path) {
		super(timeStamp, status, error, path);
	}

	public void addError(String fieldName, String message) {
		errors.removeIf(x -> x.getFieldName().equals(fieldName));
		errors.add(new FieldError(fieldName, message));
	}

	public List<FieldError> getErrors() {
		return errors;
	}

}
