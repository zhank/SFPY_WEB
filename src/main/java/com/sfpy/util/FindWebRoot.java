package com.sfpy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 查找页面系统的根路径，即作为war包形式部署时，应用的根路径，
 * 如果是自带http服务的，则是查找对应的WebContent路径。
 * 使用使用conf.ini作为判断依据<br>
 * 由于一般使用本类时，系统还未初始化，因此此类中不使用日志类记录日志
 * @author xujun
 * 
 */
public class FindWebRoot {

	private static Class<?> claz = FindWebRoot.class;
	private static final String CHECKFILENAME = File.separator +"WEB-INF" + File.separator +
										"conf" + File.separator + "spring-mvc.xml";

	/**
	 * 作为独立应用启动时的应用根路径
	 */
	private static String AppRoot;

	public static String getAppRoot() throws IOException, FileNotFoundException {

		if (AppRoot == null) {
			// 采用List存储多个相对路径，用于寻找真正的WebRoot
			List<String> relativePathList = new ArrayList<String>();
			// 先找类在$WEBROOTPATH/WEB-INF/lib目录中jar包，而配置在$WEBROOTPATH/WEB-INF/conf的情况的情况
			relativePathList.add("./../");
			// 再找类在$WEBROOTPATH/WEB-INF/classes目录，而配置在$WEBROOTPATH/WEB-INF/conf的情况
			relativePathList.add("../../");
			// 再找类在$WEBROOTPATH/../lib目录中jar包，即lib目录和$WEBROOTPATH平行的情况，而配置在$WEBROOTPATH/WEB-INF/conf的情况
			relativePathList.add("./WebContent/");
			relativePathList.add("./webapp/");
			// 再找类在$WEBROOTPATH/../classes目录中，即classes目录和$WEBROOTPATH平行的情况，而配置在$WEBROOTPATH/WEB-INF/conf的情况
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
				// 再找类在$WEBROOTPATH/WEB-INF/classes目录，而配置在$WEBROOTPATH/WEB-INF/classes/conf的情况
				path = Path.getRootPath(claz);// 这种情况仅用于类测试
				File context = new File(path + CHECKFILENAME);
				if (!context.exists()) {
					throw new IOException("程序被破坏或位置不正确，不能找到应用根路径");
				}
			}
			AppRoot = path;
		}
		return AppRoot;
	}

	/**
	 * 设置根路径的查找类
	 * @param claz
	 */
	public static void setClaz(Class<?> claz) {
		FindWebRoot.claz = claz;
	}

}
