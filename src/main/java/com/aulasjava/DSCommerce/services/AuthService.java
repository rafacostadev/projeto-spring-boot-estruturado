package com.aulasjava.DSCommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aulasjava.DSCommerce.entities.User;
import com.aulasjava.DSCommerce.exceptions.ForbiddenException;

@Service
public class AuthService {

	@Autowired
	private UserService service;

	public void validateSelfOrAdmin(long userId) {
		User user = service.authenticated();
		if (!user.hasAuthority("ROLE_ADMIN") && !user.getId().equals(userId)) {
			throw new ForbiddenException("Acesso negado!");
		}
	}
}
