package com.api.controller;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.TokenRefreshException;
import com.api.entity.User;
import com.api.model.CustomUserDetail;
import com.api.model.RefreshToken;
import com.api.security.jwt.JwtTokenProvider;
import com.api.security.payload.LoginRequest;
import com.api.security.payload.LoginResponse;
import com.api.security.payload.TokenRefreshRequest;
import com.api.security.payload.TokenRefreshResponse;
import com.api.service.RefreshTokenService;
import com.api.service.UserService;

@RestController
@RequestMapping(value = "/v1")
//@CrossOrigin(origins = {"https://fitvn.herokuapp.com", "http://fitvn.herokuapp.com", "http://localhost:3000"})
public class LoginController {

	private final String REFRESH_KEY = "mykeyrefresh";

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	UserService userService;

	RefreshTokenService refreshTokenService;
	
	@Autowired
	public LoginController(RefreshTokenService refreshTokenService) {
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		// Authentication with username and password
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		// If ok then
		SecurityContextHolder.getContext().setAuthentication(auth);

		// create jwt
		String jwt = tokenProvider.generateToken((CustomUserDetail) auth.getPrincipal());

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.UK )
                .withZone( ZoneId.systemDefault() );
		String expiryDate = formatter.format(refreshToken.getExpiryDate());
		
		Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken.getToken());
		refreshTokenCookie.setHttpOnly(true);  // only allows HTTPS
//		refreshTokenCookie.setSecure(true);
		response.addCookie(refreshTokenCookie);
		
		// create response and put jwt
		return new LoginResponse(jwt, refreshToken.getToken(), expiryDate);
	}

	@PostMapping("/register")
	public ResponseEntity<Void> registerUser(@Valid @RequestBody User user) {
		String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encodedPassword);
		userService.registerUser(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();
		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser).map(user -> {
					String token = tokenProvider.generateToken(new CustomUserDetail(user));
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}
}
