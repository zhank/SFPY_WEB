package com.sfpy.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public class EvnCheck {
	private static final Log logger = LogFactory.getLog(EvnCheck.class);
	private static final String LOG4JFILE = "/WEB-INF/conf/log4j.properties";
	private static boolean isLog4jCfgLoaded = false;

	public static void loadLog4jCfg() {
		if (!isLog4jCfgLoaded) {
			String log4jFileName;
			try {
				log4jFileName = FindWebRoot.getAppRoot() + LOG4JFILE;
				File flog4j = new File(log4jFileName);
				if (flog4j.exists()) {
					PropertyConfigurator.configure(log4jFileName);
					isLog4jCfgLoaded = true;
				}
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
	}
}
