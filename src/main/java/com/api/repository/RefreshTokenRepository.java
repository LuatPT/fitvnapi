package com.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Override
	Optional<RefreshToken> findById(Long id);

	Optional<RefreshToken> findByToken(String token);
}