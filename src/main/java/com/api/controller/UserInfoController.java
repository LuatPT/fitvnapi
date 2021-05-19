package com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.common.CommonClass;
import com.api.entity.UserInfo;
import com.api.model.DxoGetUserInfoDto;
import com.api.model.Result;
import com.api.service.UserInfoService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value="/v1")
public class UserInfoController {
	private UserInfoService userInfoService;
	
	@Autowired
	public UserInfoController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getUserInfos")
	public Result index (@RequestBody DxoGetUserInfoDto requestGetUI) {
		return CommonClass.createResult(userInfoService.getUserInfoByUserName(requestGetUI.getUserName()));
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/userInfos/{info_id}")
	public @ResponseBody Result getById (@PathVariable("info_id") Integer infoId) {
		UserInfo userInfo = userInfoService.findUserInfoById(infoId);
		return CommonClass.createResult(userInfo);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/userInfos/{info_id}")
	public @ResponseBody void updateUserInfoMethod (@PathVariable("info_id") Integer infoId, @RequestBody UserInfo userInfo) {
		userInfoService.updateUserInfo(userInfo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/userInfos")
	public @ResponseBody void addUserInfo (@RequestBody UserInfo userInfo) {
		userInfoService.insertUserInfo(userInfo);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/userInfos/{info_id}")
	public @ResponseBody void deleteUserInfo (@PathVariable("info_id") Integer infoId) {
		userInfoService.deleteUserInfo(infoId);
	}
	
}
