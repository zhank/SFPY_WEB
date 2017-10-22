package com.sfpy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfpy.entity.ResultInfo;
import com.sfpy.service.AccountQueryService;

@Controller
@RequestMapping("/account")
public class AccountQueryController {
	
	@Autowired
	private AccountQueryService accountQueryService;
	
	@RequestMapping("/accountQuery.do")
	@ResponseBody
	public ResultInfo execute(String userCode) throws Exception {
		return  accountQueryService.getUserAccountByUserCode(userCode);
	}
	
	
	
	@RequestMapping("/userBalance.do")
	@ResponseBody
	public ResultInfo getUserMoneyBalanceByUserId(String clientId) throws Exception {
		return  accountQueryService.getUserMoneyBalanceByClientId(clientId);
	}

}
