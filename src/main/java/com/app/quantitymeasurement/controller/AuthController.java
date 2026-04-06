package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.AuthResponse;
import com.app.quantitymeasurement.dto.LoginRequest;
import com.app.quantitymeasurement.dto.RegisterRequest;
import com.app.quantitymeasurement.model.AppUserEntity;
import com.app.quantitymeasurement.repository.AppUserRepository;
import com.app.quantitymeasurement.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
			AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.appUserRepository = appUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		if (appUserRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
		}
		if (appUserRepository.existsByMobileNumber(request.getMobileNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Mobile number already exists");
		}

		AppUserEntity user = new AppUserEntity();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setMobileNumber(request.getMobileNumber());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		appUserRepository.save(user);

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		String token = jwtService.generateToken(authentication);
		AuthResponse response = new AuthResponse(token, "Bearer", jwtService.getExpirationMs() / 1000,
				authentication.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String token = jwtService.generateToken(authentication);
		AuthResponse response = new AuthResponse(token, "Bearer", jwtService.getExpirationMs() / 1000,
				authentication.getName());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/oauth2/google")
	public ResponseEntity<String> googleLoginPath() {
		return ResponseEntity.ok("Use /oauth2/authorization/google to sign in with Google");
	}
}
