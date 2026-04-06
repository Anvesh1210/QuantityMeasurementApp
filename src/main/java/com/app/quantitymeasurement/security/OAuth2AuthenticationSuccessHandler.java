package com.app.quantitymeasurement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtService jwtService;

	@Value("${app.oauth2.authorized-redirect-uri:http://localhost:4200/oauth2/callback}")
	private String authorizedRedirectUri;

	public OAuth2AuthenticationSuccessHandler(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String token = jwtService.generateToken(authentication);
		String targetUrl = UriComponentsBuilder.fromUriString(authorizedRedirectUri).queryParam("token", token)
				.queryParam("tokenType", "Bearer").toUriString();

		clearAuthenticationAttributes(request);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
