package com.sfpy.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	 static Properties property = new Properties();  
	    public static boolean loadFile(String fileName){   
	        try {  
	        	String filePath = ".." + File.separator + "conf" + File.separator + fileName;
	            property.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath));  
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
