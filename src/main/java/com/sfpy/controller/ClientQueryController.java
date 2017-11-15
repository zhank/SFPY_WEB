package com.sfpy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfpy.entity.ResultInfo;
import com.sfpy.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class ClientQueryController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUserCode.do")
	@ResponseBody
	public ResultInfo execute(String clientId) throws Exception {
		return  userService.getUserAccountByClientId(clientId);
	}
	
	@RequestMapping("/getUserNameByUserCode.do")
	@ResponseBody
	public ResultInfo getUserNameByUserCode(String userCode) throws Exception {
		return  userService.getUserNameByUserCode(userCode);
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public ResultInfo execute(@RequestParam("userName") String userName, @RequestParam("password")  String password,
							  String validCode, HttpServletRequest request) {
		ResultInfo resultInfo = userService.checkLogin(userName, password, validCode, request);
		return resultInfo;
	}
}
