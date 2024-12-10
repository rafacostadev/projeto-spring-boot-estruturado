package com.aulasjava.DSCommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aulasjava.DSCommerce.entities.Role;
import com.aulasjava.DSCommerce.entities.User;
import com.aulasjava.DSCommerce.projections.UserDetailsProjection;
import com.aulasjava.DSCommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> userProjection = repository.searchUserAndRolesByEmail(username);
		if (userProjection.size() == 0) {
			throw new UsernameNotFoundException("Credenciais inválidas!");
		}
		User user = new User();
		user.setEmail(username);
		user.setPassword(userProjection.get(0).getPassword());
		for (UserDetailsProjection projection : userProjection) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		return user;
	}

}
