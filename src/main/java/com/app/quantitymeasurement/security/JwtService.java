package com.app.quantitymeasurement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class JwtService {

	private final Key signingKey;
	private final long expirationMs;

	public JwtService(@Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-ms}") long expirationMs) {
		this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
		this.expirationMs = expirationMs;
	}

	public String generateToken(Authentication authentication) {
		List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
		if (authentication.getPrincipal() instanceof OAuth2User
				&& authorities.stream().noneMatch(auth -> auth.getAuthority().startsWith("ROLE_"))) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		return generateToken(resolveSubject(authentication), authorities);
	}

	public String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
		Date issuedAt = new Date();
		Date expiryAt = new Date(issuedAt.getTime() + expirationMs);

		return Jwts.builder().setSubject(subject).claim("roles",
				authorities.stream().map(GrantedAuthority::getAuthority).toList()).setIssuedAt(issuedAt)
				.setExpiration(expiryAt).signWith(signingKey, SignatureAlgorithm.HS256).compact();
	}

	public String extractSubject(String token) {
		return extractAllClaims(token).getSubject();
	}

	public List<SimpleGrantedAuthority> extractAuthorities(String token) {
		Object rolesClaim = extractAllClaims(token).get("roles");
		if (!(rolesClaim instanceof Collection<?> roles)) {
			return List.of();
		}

		return roles.stream().filter(Objects::nonNull).map(Object::toString).map(SimpleGrantedAuthority::new).toList();
	}

	public boolean isTokenValid(String token) {
		try {
			Claims claims = extractAllClaims(token);
			return claims.getExpiration() != null && claims.getExpiration().after(new Date());
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	public long getExpirationMs() {
		return expirationMs;
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
	}

	private String resolveSubject(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails userDetails) {
			return userDetails.getUsername();
		}
		if (principal instanceof OAuth2User oAuth2User) {
			Object email = oAuth2User.getAttribute("email");
			if (email != null) {
				return email.toString();
			}
		}
		return authentication.getName();
	}
}
