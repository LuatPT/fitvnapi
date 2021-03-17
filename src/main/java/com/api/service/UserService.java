package com.api.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	UserDetails getByUsername(String username);
	UserDetails getByUserId(Long userId);
}
