package com.api.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.common.CommonClass.Action;
import com.api.entity.User;
import com.api.model.CustomUserDetail;
import com.api.repository.UserRepository;
import com.api.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails getByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user== null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new CustomUserDetail(user);
	}

	//JwtAuthenticationFilter will use
	@Transactional
	public UserDetails getByUserId(Long userId) {
		User user = userRepository.findByUserId(userId);
		return new CustomUserDetail(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new CustomUserDetail(userRepository.findByUsername(username));
	}

	@Override
	public void registerUser(User user) {
		userRepository.saveOrUpdateFood(user, Action.ADD);
	}
	
	
	
}
