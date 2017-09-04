package com.sfpy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * ��·��������
 * 
 * @author SFPY
 */
public class FindRoot {
	private static Class<?> claz = FindRoot.class;

	public static final String CHECKFILENAME = "/WEB-INF/conf/spring-mvc.xml";
	private static final String CHECKFILENAME_TEST = "/conf/spring-mvc.xml";
	/**
	 * ��Ϊ����Ӧ������ʱ��Ӧ�ø�·����
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
		// ����List�洢������·��������Ѱ��������WebRoot��Ȼ��ʹ�������·����ȷ�ϸ���һ����AppRoot
		List<String> relativePathList = new ArrayList<String>();
		// ��������$WEBROOTPATH/WEB-INF/libĿ¼��jar������������$WEBROOTPATH/WEB-INF/conf����������
		relativePathList.add("./../");
		// ��������$WEBROOTPATH/WEB-INF/classesĿ¼����������$WEBROOTPATH/WEB-INF/conf�����
		relativePathList.add("../../");
		// ��������$WEBROOTPATH/../libĿ¼��jar��(��$REALAPTH/libĿ¼)����libĿ¼��$WEBROOTPATHƽ�е��������������$WEBROOTPATH/WEB-INF/conf�����
		relativePathList.add("./WebContent/");
		relativePathList.add("./webapp/");
		// ��������$WEBROOTPATH/../classesĿ¼��(��$REALAPTH/classesĿ¼)����classesĿ¼��$WEBROOTPATHƽ�е��������������$WEBROOTPATH/WEB-INF/conf�����
		relativePathList.add("../WebContent/");
		relativePathList.add("../webapp/");

		boolean isFinded = false;
		String path = null;
		for (int i = 0; i < relativePathList.size(); i++) {
			path = Path.getRootRelativePath(relativePathList.get(i), claz);
			File context = new File(path + CHECKFILENAME);
			if (context.exists()) {
				// �ҵ���������ѭ��
				isFinded = true;
				break;
			} else {
				// δ�ҵ���������һ��
				continue;
			}
		}
		if (!isFinded) {
			// ����δ�ҵ�
			// ��������classesĿ¼����������classes/conf�����
			path = Path.getRootPath(claz);// ������������������
			File context = new File(path + CHECKFILENAME_TEST);
			if (!context.exists()) {
				System.out.println(context);
				throw new IOException("�����ƻ���λ�ò���ȷ�������ҵ�Ӧ�ø�·����" + "������Ŀ¼�±������/conf/spring-mvc.xml�ļ�");
			}
		}
		WebRoot = new File(path).getCanonicalPath();
		// ʵ�ʵ�appPathӦ��������һ��
		AppRoot = new File(path + "/../").getCanonicalPath();
		return AppRoot;
	}
}
