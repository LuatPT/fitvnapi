package com.api.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.TokenRefreshException;
import com.api.model.RefreshToken;
import com.api.repository.RefreshTokenRepository;
import com.api.repository.UserRepository;
import com.api.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

	public static final Long refreshTokenDurationMs = 86400000L;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(String userName) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findByUsername(userName));
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	@Transactional
	public void deleteByUserId(Long userId) {
		refreshTokenRepository.deleteById(userRepository.findByUserId(userId).getUserId());
	}
}
