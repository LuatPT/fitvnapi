package com.api.service;

import java.util.Optional;

import com.api.model.RefreshToken;

public interface RefreshTokenService {

	Optional<RefreshToken> findByToken(String requestRefreshToken);

	public RefreshToken createRefreshToken(String userName);
	
	public RefreshToken verifyExpiration(RefreshToken token);
	
	public void deleteByUserId(Long userId);
}
