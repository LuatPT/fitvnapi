package com.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.api.entity.User;

public interface UserService extends UserDetailsService {
	UserDetails getByUsername(String username);
	UserDetails getByUserId(Long userId);
	void registerUser(User user);
}
