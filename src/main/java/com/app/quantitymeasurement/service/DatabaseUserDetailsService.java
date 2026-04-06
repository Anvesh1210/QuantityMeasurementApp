package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.AppUserEntity;
import com.app.quantitymeasurement.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

	private final AppUserRepository appUserRepository;

	public DatabaseUserDetailsService(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUserEntity user = appUserRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found for email: " + email));

		return User.withUsername(user.getEmail()).password(user.getPassword()).authorities(normalizeRole(user.getRole()))
				.disabled(!user.isEnabled()).build();
	}

	private String normalizeRole(String role) {
		String raw = role == null ? "" : role.trim();
		if (raw.isEmpty()) {
			return "ROLE_USER";
		}
		return raw.startsWith("ROLE_") ? raw : "ROLE_" + raw;
	}
}
