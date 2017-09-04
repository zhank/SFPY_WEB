/*
 * Created on 2006-11-6
 *
 * @author renwei
 * 
 * Copyright (C) 2006 KOAL SOFTWARE.
 */
package com.sfpy.db;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * �������IDbOp��ʵ���࣬�ṩע��ӿڣ����ݿ���Ż����ע���ʹ�á�Ŀǰȱʡֻע����֪���Ż��ࣨ��regDefault����
 * �����д���Ż�����Ҫ��getDbOpǰע��󣬲��ܱ���ȡ��
 * @author renwei
 * @version 1.0
 * @since 1.0
 */
public class DbOpMgr {
	final static Logger log = LoggerFactory.getLogger(DbOpMgr.class);
	
	/**
	 * ע���õ�map
	 */
	private static Map<String, IDbOp> m_regs = new HashMap<String, IDbOp>();
	
	/**
	 * �Ƿ��Ѿ���ʼ������û�е���ȱʡע��
	 */
	private static boolean m_init = false;
	
	private DbOpMgr() {
		// ��̬��
	}
	
	/**
	 * ����jdbcDriver��version����һ���ַ�����ʶ
	 * @param jdbcDriver
	 * @param version
	 * @return
	 */
	private static String getID(String jdbcDriver, int version) {
		return jdbcDriver + "(" + version + ")";
	}
	
	/**
	 * ע��IDbOp�Ż���
	 * @param jdbcDriver
	 * @param version
	 * @param dbOp
	 */
	public static void register(String jdbcDriver, int version, IDbOp dbOp) {
		m_regs.put(getID(jdbcDriver, version), dbOp);
	}
	
	/**
	 * ��ȡIDbOpʵ�֡����������Ż��࣬�õ��ľ���IDbOpBaseͨ����
	 * @param jdbcDriver
	 * @param version
	 * @return
	 */
	public static IDbOp getDbOp(String jdbcDriver, int version) {
		if (!m_init) {
			regDefault();
			m_init = true;
		}
		IDbOp dbOp = (IDbOp)m_regs.get(getID(jdbcDriver, version));
		if (dbOp == null)
			dbOp = new DbOpBase();
		return dbOp;
	}
	
	/**
	 * ע����֪���Ż��࣬ע�ᷢ�����Ż��౻����ʱ
	 */
	private static void regDefault() {
		try {
		    // ע�����ݿ��Ż���,���о�̬�����Զ�ע��
		    DbOpOracle.register();
		} catch (Exception e) {
			log.error("ע��ȱʡ���ݿ��Ż���ʧ��", e);
		}
	}
	
}
