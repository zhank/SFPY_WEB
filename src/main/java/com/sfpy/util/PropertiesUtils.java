package com.sfpy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	 static Properties property = new Properties();  
	    public static boolean loadFile(String fileName){   
	        try {  
	        	String appBase = FindWebRoot.getAppRoot();
	        	String filePath = appBase + File.separator + "WEB-INF" + File.separator + "conf" + File.separator + fileName;
	        	property.load(new FileInputStream(filePath));
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            return false;     
	        }     
	        return true;  
	    }  
	    public static String getPropertyValue(String key){     
	        return property.getProperty(key);  
	    }  
}
