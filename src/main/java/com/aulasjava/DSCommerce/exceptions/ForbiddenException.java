package com.aulasjava.DSCommerce.exceptions;

@SuppressWarnings("serial")
public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String msg) {
		super(msg);
	}
}
