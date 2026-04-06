package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.model.AppUserEntity;
import com.app.quantitymeasurement.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthDataInitializer implements CommandLineRunner {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.auth.name:App User}")
	private String defaultName;

	@Value("${app.auth.email:appuser@example.com}")
	private String defaultEmail;

	@Value("${app.auth.password:password123}")
	private String defaultPassword;

	@Value("${app.auth.mobile-number:+10000000000}")
	private String defaultMobileNumber;

	@Value("${app.auth.roles:USER}")
	private String defaultRoles;

	public AuthDataInitializer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (appUserRepository.existsByEmail(defaultEmail)) {
			return;
		}
		if (appUserRepository.existsByMobileNumber(defaultMobileNumber)) {
			return;
		}

		AppUserEntity defaultUser = new AppUserEntity();
		defaultUser.setName(defaultName);
		defaultUser.setEmail(defaultEmail);
		defaultUser.setMobileNumber(defaultMobileNumber);
		defaultUser.setPassword(passwordEncoder.encode(defaultPassword));
		defaultUser.setRole(resolveRole(defaultRoles));
		defaultUser.setEnabled(true);

		appUserRepository.save(defaultUser);
	}

	private String resolveRole(String rolesCsv) {
		String[] roles = rolesCsv.split(",");
		for (String role : roles) {
			String trimmedRole = role.trim();
			if (!trimmedRole.isEmpty()) {
				return trimmedRole.startsWith("ROLE_") ? trimmedRole : "ROLE_" + trimmedRole;
			}
		}
		return "ROLE_USER";
	}
}
