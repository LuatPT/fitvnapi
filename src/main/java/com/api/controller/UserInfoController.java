package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.entity.UserInfo;
import com.api.model.DxoGetUserInfoDto;
import com.api.service.UserInfoService;

@RestController
@RequestMapping(value = "/v1")
//@CrossOrigin(origins = {"https://fitvn.herokuapp.com", "http://fitvn.herokuapp.com", "http://localhost:3000"})
public class UserInfoController {
	private UserInfoService userInfoService;

	@Autowired
	public UserInfoController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getUserInfos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfo> index(@RequestBody DxoGetUserInfoDto requestGetUI) {
		UserInfo userInfo = userInfoService.getUserInfoByUserName(requestGetUI.getUserName());
		if (userInfo == null) {
			return new ResponseEntity<UserInfo>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/userInfos/{info_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserInfo> getById(@PathVariable("info_id") Integer infoId) {
		UserInfo userInfo = userInfoService.findUserInfoById(infoId);
		if (userInfo == null) {
			return new ResponseEntity<UserInfo>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/userInfos/{info_id}")
	public ResponseEntity<UserInfo> updateUserInfoMethod(@PathVariable("info_id") Integer infoId,
			@RequestBody UserInfo userInfo) {
		UserInfo currentUserInfo = userInfoService.findUserInfoById(infoId);
		if (currentUserInfo == null) {
			return new ResponseEntity<UserInfo>(HttpStatus.NOT_FOUND);
		}
		userInfoService.updateUserInfo(userInfo);
		return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/userInfos")
	public ResponseEntity<Void> addUserInfo(@RequestBody UserInfo userInfo) {
		userInfoService.insertUserInfo(userInfo);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/userInfos/{info_id}")
	public ResponseEntity<UserInfo> deleteUserInfo(@PathVariable("info_id") Integer infoId) {
		UserInfo currentUserInfo = userInfoService.findUserInfoById(infoId);
		if (currentUserInfo == null) {
			return new ResponseEntity<UserInfo>(HttpStatus.NOT_FOUND);
		}
		userInfoService.deleteUserInfo(infoId);
		return new ResponseEntity<UserInfo>(HttpStatus.NO_CONTENT);
	}

}
