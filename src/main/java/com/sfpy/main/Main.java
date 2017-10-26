package com.sfpy.main;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.WebXml;
import org.apache.catalina.session.FileStore;
import org.apache.catalina.session.PersistentManager;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.WebRuleSet;
import org.apache.tomcat.util.descriptor.DigesterFactory;
import org.apache.tomcat.util.digester.Digester;

import com.sfpy.util.EvnCheck;
import com.sfpy.util.FindWebRoot;

public class Main {
	
	//private static final Logger logger = LoggerFactory.getLogger(Main.class);
	/**
	 * 共享会话
	 */
	protected static boolean shareSession = false;
	
	public static void main(String[] args) throws Exception {
		EvnCheck.loadLog4jCfg();


		//idea测试
		Tomcat tomcat = new Tomcat();
		tomcat.setHostname("localhost");
		tomcat.setPort(8088);
		
		String baseDir = FindWebRoot.getAppRoot();
		tomcat.setBaseDir(baseDir);
		
		String appBase = baseDir;
		
		File f = new File(appBase + "/WEB-INF/web.xml");
		if (!f.exists()) {
			appBase = baseDir + File.separator + "webapp";
			f = new File(appBase + "/WEB-INF/conf/spring-mvc.xml");
			if (!f.exists()) {
				throw new Exception("无法找到页面目录" + appBase);
			}
		}
		System.out.println("Finded Base Web Root is :" + appBase);
		System.out.println("登陆地址:http://localhost:8088");

		File globalWebXml = new File(baseDir + "/WIN-INF/web.xml");
		Map<String, String> mimeMappings = parseMimeMappingFromWebXml(globalWebXml
				.getCanonicalPath());
		// 2.4. Context
		Context context = tomcat.addWebapp("/",
				new File(appBase).getAbsolutePath());
		StandardContext standardContext = (StandardContext) context;

		if (mimeMappings != null) {
			for (String key : mimeMappings.keySet()) {
				standardContext.addMimeMapping(key, mimeMappings.get(key));
			}
		}
		standardContext.setCrossContext(shareSession);

		// 下面三行，解决因未实现序列化而报的异常java.io.WriteAbortedException
		PersistentManager persistentManager = new PersistentManager();
		persistentManager.setSaveOnRestart(false);
		persistentManager.setStore(new FileStore());
		standardContext.setManager(persistentManager);
		tomcat.start();
		tomcat.getServer().await();
	}
	
	/**
	 * parse the web.xml file, and get the MimeMapping map object
	 * 
	 * @param webXmlFilePath
	 *            web.xml filepath
	 * @return return the map object of MimeMapping in web.xml file, if failed
	 *         to parse, the map object is empty.
	 */
	protected static Map<String, String> parseMimeMappingFromWebXml(
			String webXmlFilePath) {

		Map<String, String> mimeMappings = new HashMap<String, String>();

		File webXmlFile = new File(webXmlFilePath);
		if (!webXmlFile.exists()) {
			return mimeMappings;
		}

		FileInputStream is = null;
		try {
			WebXml webXml = new WebXml();
			WebRuleSet webRuleSet = new WebRuleSet(false);
			Digester webDigester;
			webDigester = DigesterFactory.newDigester(false, false, webRuleSet, true);
			webDigester.getParser();
			webDigester.push(webXml);
			is = new FileInputStream(webXmlFile);
			webDigester.parse(is);

			if (webXml.getMimeMappings() != null
					&& webXml.getMimeMappings().size() > 0) {
				mimeMappings = webXml.getMimeMappings();
			}

		} catch (Exception e) {
			System.out.println("Failed to parse the '" + webXmlFilePath + "' file: "
					+ e.getMessage());
		} finally {
			//FileTools.iStreamClose(is);
		}
		return mimeMappings;
	}
}
