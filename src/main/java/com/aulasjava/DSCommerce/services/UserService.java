package com.aulasjava.DSCommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aulasjava.DSCommerce.DTO.UserDTO;
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

	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = authenticated();
		return new UserDTO(user);
	}

	protected User authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			User user = repository.findByEmail(username).get();
			return user;
		} catch (Exception e) {
			throw new UsernameNotFoundException("Email não encontrado!");
		}
	}

}
