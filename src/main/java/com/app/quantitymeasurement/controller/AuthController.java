package com.app.quantitymeasurement.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
            AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", true, "message", "Email already exists")); // ✅ FIX
        }

        if (repository.existsByMobileNumber(request.getMobileNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", true, "message", "Mobile number already exists")); // ✅ FIX
        }

        AppUserEntity user = new AppUserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);

        repository.save(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtService.generateToken(auth);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Bearer", jwtService.getExpirationMs() / 1000, auth.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtService.generateToken(auth);

        return ResponseEntity.ok(
                new AuthResponse(token, "Bearer", jwtService.getExpirationMs() / 1000, auth.getName()));
    }
}