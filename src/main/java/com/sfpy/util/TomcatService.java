package com.sfpy.util;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.IntrospectionUtils;

/**
 * tomcat服务启动类
 * 
 * @author SFPY
 *
 */
public class TomcatService {
	/**
	 * http绑定IP，默认为所有IP
	 */
	protected static String httpBindIP = "0.0.0.0";
	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		Connector httpConnector = tomcat.getConnector();
		httpConnector.setPort(8089);
		httpConnector.setMaxPostSize(0);
		httpConnector.setURIEncoding("UTF-8");

		IntrospectionUtils.setProperty(httpConnector, "address", httpBindIP);

		// 2.8. Start
		tomcat.getServer().start();
	}

}
	