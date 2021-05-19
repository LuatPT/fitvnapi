package com.api.service;

import com.api.entity.UserInfo;

public interface UserInfoService {

	public UserInfo getUserInfoByUserName(String userName) ;

	public UserInfo findUserInfoById(int infoId);

	public void updateUserInfo(UserInfo userInfo);

	public void insertUserInfo(UserInfo userInfo);

	public void deleteUserInfo(int infoId);

}
