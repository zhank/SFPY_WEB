package com.sfpy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sfpy.constant.BankInfo;
import com.sfpy.constant.ResultStatus;
import com.sfpy.constant.UserStatus;
import com.sfpy.dao.TbSfpyClientDao;
import com.sfpy.entity.ResultInfo;
import com.sfpy.entity.TbSfpyClient;

@Service
public class AccountQueryService {

	public ResultInfo getUserAccountByUserCode(String userCode) throws Exception {
		ResultInfo result = new ResultInfo();

		StringBuffer cond = new StringBuffer();
		cond.append(TbSfpyClient.CLIENT_IDENTITY.toSqlEQ(userCode)).append(" AND ")
				.append(TbSfpyClient.CLIENT_STATUS.toSqlEQ(UserStatus.NORMAL.getId()));

		List<Map<String, Object>> userData = TbSfpyClientDao.getInstance().searchByCond(cond.toString());
		List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
		if (userData != null && !userData.isEmpty()) {
			Map<String, Object> userMap = userData.get(0);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//Object clientId = userMap.get(TbSfpyClient.CLIENT_ID.name);
			Object clientName = userMap.get(TbSfpyClient.CLIENT_NAME.name);
			Object clientBankId = userMap.get(TbSfpyClient.CLIENT_BANK_ID.name);
			Object clientIdentity = userMap.get(TbSfpyClient.CLIENT_IDENTITY.name);
			String bankName = BankInfo.UNKNOWN.getDesc();
			if(clientBankId != null) {
				bankName = BankInfo.getBankName((Integer)clientBankId);
			}
			Object clientBalance = userMap.get(TbSfpyClient.CLIENT_BALANCE.name);
			Object accountCode = userMap.get(TbSfpyClient.CLIENT_ACCOUNT_CODE.name);
			
			//dataMap.put(TbSfpyClient.CLIENT_ID.name, clientId);
			dataMap.put(TbSfpyClient.CLIENT_NAME.name, clientName);
			dataMap.put(TbSfpyClient.CLIENT_IDENTITY.name, clientIdentity);
			dataMap.put(TbSfpyClient.CLIENT_BANK_ID.name, bankName);
			dataMap.put(TbSfpyClient.CLIENT_BALANCE.name, clientBalance);
			dataMap.put(TbSfpyClient.CLIENT_ACCOUNT_CODE.name, accountCode);
			resultData.add(dataMap);
			String jsonStr = JSON.toJSONString(resultData);
			result.setData(jsonStr);
			result.setMsg("查询成功");
			result.setStatus(10);
			return result;
		}
		return result;
	}
	
	/**
	 * 获取用户余额
	 * @param userId
	 * @return
	 * @throws Exception 
	 */

	public ResultInfo getUserMoneyBalanceByClientId(String clientId) throws Exception {
		ResultInfo result = new ResultInfo();
		
		if (clientId != null) {
			StringBuffer accoutCond = new StringBuffer();
			accoutCond.append(TbSfpyClient.CLIENT_ID.toSqlEQ(clientId));
			Map<String, Object> dataMap = TbSfpyClientDao.getInstance().getDataById(clientId);
			if(dataMap != null && !dataMap.isEmpty()) {
				Object balance = dataMap.get(TbSfpyClient.CLIENT_BALANCE.name);
				if(balance != null) {
					result.setData(balance);
					result.setStatus(ResultStatus.SUCCESS.getId());
					result.setMsg(ResultStatus.SUCCESS.getDesc());
				} else {
					result.setData(null);
					result.setStatus(ResultStatus.FAIL.getId());
					result.setMsg(ResultStatus.FAIL.getDesc());
				}
			}
		}
		return result;
	}

}
