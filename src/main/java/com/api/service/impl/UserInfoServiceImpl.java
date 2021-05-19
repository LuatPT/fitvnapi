package com.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.common.CommonClass.Action;
import com.api.entity.UserInfo;
import com.api.repository.UserInfoRepository;
import com.api.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
		super();
		this.userInfoRepository = userInfoRepository;
	}

	@Override
	public UserInfo getUserInfoByUserName(String userName) {
		return userInfoRepository.getUserInfoFromDB(userName);
	}

	@Override
	public UserInfo findUserInfoById(int infoId) {
		return userInfoRepository.getUserInfoById(infoId);
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		userInfoRepository.saveOrUpdateUserInfo(userInfo, Action.UPDATE);
	}

	@Override
	public void insertUserInfo(UserInfo userInfo) {
		userInfoRepository.saveOrUpdateUserInfo(userInfo, Action.ADD);
	}

	@Override
	public void deleteUserInfo(int infoId) {
		userInfoRepository.deleteUserInfo(infoId);
		
	}

}
