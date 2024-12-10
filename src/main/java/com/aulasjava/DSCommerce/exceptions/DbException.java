package com.aulasjava.DSCommerce.exceptions;

@SuppressWarnings("serial")
public class DbException extends RuntimeException {
	public DbException(String msg) {
		super(msg);
	}
}
