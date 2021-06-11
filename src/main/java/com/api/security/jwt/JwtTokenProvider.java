package com.api.security.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.api.model.CustomUserDetail;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	// private key only server know
	private final String JWT_KEY_SECRET = "mykeysecret";
	
	private final long JWT_EXPIRE = 604800000L;
	
	//create new token with user
	public String generateToken(CustomUserDetail userDetail) {
		Date now = new Date();
		// time to expire this Jwt
		Date expireDate = new Date(now.getTime() + JWT_EXPIRE);
		
		// return a jwt string
		return Jwts.builder()
			.setSubject(Long.toString(userDetail.getUser().getUserId() ))
			.setIssuedAt(now) // hieu luc ngay lap tuc
			.setExpiration(expireDate)
			.signWith(SignatureAlgorithm.HS512, JWT_KEY_SECRET)
			.compact()
			;
	}
	
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(JWT_KEY_SECRET)
				.parseClaimsJws(token)
				.getBody();
		return Long.parseLong(claims.getSubject());
	}
	
	 public boolean validateToken(String authToken, String tokenKey) {
    	 try {
	    	Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(authToken);
	    	return true;
    	 }catch (MalformedJwtException ex) {
             log.error("Invalid JWT token");
         } catch (ExpiredJwtException ex) {
             log.error("Expired JWT token");
         } catch (UnsupportedJwtException ex) {
             log.error("Unsupported JWT token");
         } catch (IllegalArgumentException ex) {
             log.error("JWT claims string is empty.");
         }
    	return false;
    }
	
}
