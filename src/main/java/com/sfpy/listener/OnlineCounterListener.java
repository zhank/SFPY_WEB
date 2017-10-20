package com.sfpy.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sfpy.cfg.SysCfg;
/**
 * 2014��11��26��18:08:25
 * @author zhangmh ͳ����������
 *
 */
public class OnlineCounterListener implements HttpSessionListener,ServletContextListener  {
	public ServletContext appclication = null;
	public static long count = 0;
	public static final String ON_LINE_COUNT = "onlineCount";
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// ����session����ʱ��
		int timeOut = SysCfg.sessionTimeOut;
		se.getSession().setMaxInactiveInterval(timeOut * 60);
				
		appclication.setAttribute(ON_LINE_COUNT, increment());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		appclication.setAttribute(ON_LINE_COUNT, decrement());
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		appclication = sce.getServletContext();		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		appclication = null;
	}
	
	public synchronized long increment(){
		return ++count;
	}
	
	public synchronized long decrement(){
		return --count;
	}
}
