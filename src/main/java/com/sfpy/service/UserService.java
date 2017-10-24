package com.sfpy.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sfpy.constant.ResultStatus;
import com.sfpy.dao.TbSfpyClientDao;
import com.sfpy.entity.ResultInfo;
import com.sfpy.entity.TB_SFPY_USER;
import com.sfpy.entity.TbSfpyClient;

@Service
public class UserService {

	public List<TB_SFPY_USER> getAllUser() {
		return null;
	}

	public void addUser(TB_SFPY_USER user) {

	}

	public void updateUser(String userId) {

	}

	public void delelteUser(String userId) {

	}

	/**
	 * ��ѯ��½
	 * @param userName
	 * @param password
	 * @return
	 */
	public ResultInfo checkLogin(String userName, String password) {
		ResultInfo result = new ResultInfo();
		StringBuffer cond = new StringBuffer();
		cond.append(TbSfpyClient.CLIENT_NAME.toSqlEQ(userName)).append(" AND ")
				.append(TbSfpyClient.CLIENT_PSWD.toSqlEQ(password));

		try {
			List<Map<String, Object>> userList = TbSfpyClientDao.getInstance().searchByCond(cond.toString());
			if (userList != null && !userList.isEmpty()) {
				Map<String, Object> dataMap = userList.get(0);
				Object userId = dataMap.get(TbSfpyClient.CLIENT_ID.name);
				if (userId != null) {
					System.out.println("��½�ɹ�");
					result.setStatus(10);
					result.setMsg("��½�ɹ���");
					result.setData(userId);
					;
				}
			} else {
				System.out.println("��֤ʧ�ܣ�");
				result.setStatus(-1);
				result.setMsg("");
			}
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * ����clientId��ȡ�û���Ϣ
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public ResultInfo getUserAccountByClientId(Object clientId) throws Exception {
		ResultInfo result = null;
		Map<String, Object> userData = TbSfpyClientDao.getInstance().getDataById(clientId);
		if(userData != null) {
			Object userCode = userData.get(TbSfpyClient.CLIENT_IDENTITY.name);
			if(userCode != null) {
				result = new ResultInfo();
				result.setStatus(ResultStatus.SUCCESS.getId());
				result.setMsg(ResultStatus.SUCCESS.getDesc());
				result.setData(userCode);;
				return result;
			}
		}
		return result;
	}

}
