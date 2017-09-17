package com.sfpy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfpy.entity.ResultInfo;
import com.sfpy.service.UserService;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public ResultInfo execute(String userName, String password) {
		ResultInfo resultInfo = userService.checkLogin(userName, password);
		return resultInfo;
	}

}
