package com.sfpy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 根路径查找类
 * 
 * @author SFPY
 */
public class FindRoot {
	private static Class<?> claz = FindRoot.class;

	public static final String CHECKFILENAME = "/WEB-INF/conf/spring-mvc.xml";
	private static final String CHECKFILENAME_TEST = "/conf/spring-mvc.xml";
	/**
	 * 作为独立应用启动时的应用根路径，
	 */
	private static String AppRoot;
	private static String WebRoot;

	public static String getWebRoot() throws IOException, FileNotFoundException {
		if (WebRoot == null) {
			getAppRoot();
		}
		return WebRoot;
	}

	public static String getAppRoot() throws IOException, FileNotFoundException {

		if (AppRoot != null) {
			return AppRoot;
		}
		// 采用List存储多个相对路径，用于寻找真正的WebRoot，然后使用其相对路径，确认更上一级的AppRoot
		List<String> relativePathList = new ArrayList<String>();
		// 先找类在$WEBROOTPATH/WEB-INF/lib目录中jar包，而配置在$WEBROOTPATH/WEB-INF/conf的情况的情况
		relativePathList.add("./../");
		// 再找类在$WEBROOTPATH/WEB-INF/classes目录，而配置在$WEBROOTPATH/WEB-INF/conf的情况
		relativePathList.add("../../");
		// 再找类在$WEBROOTPATH/../lib目录中jar包(即$REALAPTH/lib目录)，即lib目录和$WEBROOTPATH平行的情况，而配置在$WEBROOTPATH/WEB-INF/conf的情况
		relativePathList.add("./WebContent/");
		relativePathList.add("./webapp/");
		// 再找类在$WEBROOTPATH/../classes目录中(即$REALAPTH/classes目录)，即classes目录和$WEBROOTPATH平行的情况，而配置在$WEBROOTPATH/WEB-INF/conf的情况
		relativePathList.add("../WebContent/");
		relativePathList.add("../webapp/");

		boolean isFinded = false;
		String path = null;
		for (int i = 0; i < relativePathList.size(); i++) {
			path = Path.getRootRelativePath(relativePathList.get(i), claz);
			File context = new File(path + CHECKFILENAME);
			if (context.exists()) {
				// 找到，则跳出循环
				isFinded = true;
				break;
			} else {
				// 未找到，尝试下一个
				continue;
			}
		}
		if (!isFinded) {
			// 还是未找到
			// 再找类在classes目录，而配置在classes/conf的情况
			path = Path.getRootPath(claz);// 这种情况仅用于类测试
			File context = new File(path + CHECKFILENAME_TEST);
			if (!context.exists()) {
				System.out.println(context);
				throw new IOException("程序被破坏或位置不正确，不能找到应用根路径，" + "在启动目录下必须存在/conf/spring-mvc.xml文件");
			}
		}
		WebRoot = new File(path).getCanonicalPath();
		// 实际的appPath应该再向上一级
		AppRoot = new File(path + "/../").getCanonicalPath();
		return AppRoot;
	}
}
