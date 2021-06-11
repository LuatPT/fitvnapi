package com.api.security.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.common.TokenRefreshException;
import com.api.model.CustomUserDetail;
import com.api.model.RefreshToken;
import com.api.service.RefreshTokenService;
import com.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticatonFilter extends OncePerRequestFilter {
	// private key only server know
	private final String JWT_KEY_SECRET = "mykeysecret";
	
	private final String REFRESH_KEY = "mykeyrefresh";

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
//			response.setHeader("Access-Control-Allow-Origin", "https://fitvn.herokuapp.com, http://localhost:3000");
//	        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//	        response.setHeader("Access-Control-Max-Age", "3600");
//	        response.setHeader("Access-Control-Allow-Headers","Content-Type, Accept, X-Requested-With, X-Auth-Token");
//	        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//	        if(!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
//	        	filterChain.doFilter(request, response);
//	        }
			String refreshToken = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("refresh_token"))
					.findFirst().map(Cookie::getValue).orElse(null);

			String jwt = getJwtFromRequest(request);
			if (!StringUtils.hasText(jwt) && StringUtils.hasText(refreshToken) && tokenProvider.validateToken(jwt, REFRESH_KEY)) {

				jwt = refreshTokenService.findByToken(refreshToken).map(refreshTokenService::verifyExpiration)
						.map(RefreshToken::getUser).map(user -> {
							return tokenProvider.generateToken(new CustomUserDetail(user));
						}).orElseThrow(
								() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));

				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				UserDetails userDetails = userService.getByUserId(userId);
				if (userDetails != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			// If jwt valid
			else if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt, JWT_KEY_SECRET)) {
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				UserDetails userDetails = userService.getByUserId(userId);
				if (userDetails != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			}

		} catch (Exception e) {
			log.error("failed on set user authentication", e);
		}
		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request)
			throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		// Get header with Authorization
		String bearerToken = request.getHeader("Authorization");
//		Class c = Class.forName("com.api.security.payload.LoginResponse");
//		Field tokenType = c.getDeclaredField("tokenType");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
