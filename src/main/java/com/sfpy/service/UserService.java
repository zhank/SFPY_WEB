package com.sfpy.service;

import com.sfpy.constant.ResultStatus;
import com.sfpy.constant.UserStatus;
import com.sfpy.dao.TbSfpyClientDao;
import com.sfpy.entity.ResultInfo;
import com.sfpy.entity.TB_SFPY_USER;
import com.sfpy.entity.TbSfpyClient;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
	 * 查询登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	public ResultInfo checkLogin(String userName, String password, String validCode, HttpServletRequest request) {
		ResultInfo result = new ResultInfo();

		HttpSession session = request.getSession(true);
		String verifitionCode =(String)session.getAttribute("verfitionCode");
		if(verifitionCode == null) {
			result.setMsg("验证码获取失败，请重新登陆！");
			result.setStatus(-1);
			return result;
		} else {
			if(!verifitionCode.equalsIgnoreCase(validCode)) {
				result.setMsg("验证码输入错误，请重新输入！");
				result.setStatus(-1);
				return result;
			}
		}

		StringBuffer cond = new StringBuffer();
		cond.append(TbSfpyClient.CLIENT_NAME.toSqlEQ(userName)).append(" AND ")
				.append(TbSfpyClient.CLIENT_PSWD.toSqlEQ(password));
		try {
			List<Map<String, Object>> userList = TbSfpyClientDao.getInstance().searchByCond(cond.toString());
			if (userList != null && !userList.isEmpty()) {
				Map<String, Object> dataMap = userList.get(0);
				Object userId = dataMap.get(TbSfpyClient.CLIENT_ID.name);
				if (userId != null) {
					System.out.println("登陆成功");
					result.setStatus(10);
					result.setMsg("登陆成功！");
					result.setData(userId);
					;
				}
			} else {
				System.out.println("验证失败！");
				result.setStatus(-1);
				result.setMsg("");
			}
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 获取人员姓名
	 * @return
	 */
	public ResultInfo getUserNameByUserCode(Object userCode) {
		ResultInfo result = new ResultInfo();
		StringBuffer cond = new StringBuffer();
		cond.append(TbSfpyClient.CLIENT_IDENTITY.toSqlEQ(userCode)).append(" AND ")
				.append(TbSfpyClient.CLIENT_STATUS.toSqlEQ(UserStatus.NORMAL.getId()));
		try {
			List<Map<String, Object>> userList = TbSfpyClientDao.getInstance().searchByCond(cond.toString());
			if (userList != null && !userList.isEmpty()) {
				Map<String, Object> dataMap = userList.get(0);
				Object userName = dataMap.get(TbSfpyClient.CLIENT_REALLY_NAME.name);
				if (userName != null) {
					System.out.println("登陆成功");
					result.setStatus(10);
					result.setMsg("登陆成功！");
					result.setData(userName);
					;
				}
			} else {
				System.out.println("验证失败！");
				result.setStatus(-1);
				result.setMsg("");
			}
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 根据clientId获取用户信息
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
