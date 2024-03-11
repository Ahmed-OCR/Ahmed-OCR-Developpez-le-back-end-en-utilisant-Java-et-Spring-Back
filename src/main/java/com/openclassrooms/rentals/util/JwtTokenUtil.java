package com.openclassrooms.rentals.util;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

	@Value("${security.token.jwtKey}")
	private String jwtKey;

	public String generateToken(Authentication authentication) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.SECONDS))
//				.expiresAt(now.plus(1, ChronoUnit.DAYS))
				.subject(authentication.getName())
				.build();
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
		return this.jwtEncoder().encode(jwtEncoderParameters).getTokenValue();
	}

	public boolean isTokenValid(String token) throws ExpiredJwtException {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(jwtKey.getBytes()))
					.build()
					.parseClaimsJws(token)
					.getBody();

			Date expirationDate = claims.getExpiration();
			return expirationDate != null && !expirationDate.before(new Date());
		} catch (ExpiredJwtException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
//
//	public boolean validateToken(String token) {
//		System.out.println("VALIDATE - 2");
//		final String username = getUsernameFromToken(token);
//		return isTokenExpired(token);
//	}

//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}

//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}

	private JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}

//	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}

//	private Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
//	}
//
//	private boolean isTokenExpired(String token) {
//		final Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}

}
