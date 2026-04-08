package com.app.quantitymeasurement.config;

import com.app.quantitymeasurement.security.JwtAuthenticationFilter;
import com.app.quantitymeasurement.security.OAuth2AuthenticationSuccessHandler;
import com.app.quantitymeasurement.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
						  OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> csrf.disable())

				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)

				.exceptionHandling(ex ->
						ex.authenticationEntryPoint(new RestAuthenticationEntryPoint())
				)

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(
								"/",
								"/actuator/health",
								"/swagger-ui/**",
								"/v3/api-docs/**"
						).permitAll()

						// allow auth endpoints if any
						.requestMatchers("/api/v1/auth/**").permitAll()

						// everything else
						.anyRequest().authenticated()
				);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(
				"http://localhost:4200",
				"http://127.0.0.1:4200",
				"http://localhost:3000",
				"http://localhost:8080"
		));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}