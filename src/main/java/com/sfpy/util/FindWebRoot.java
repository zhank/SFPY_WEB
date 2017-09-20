package com.sfpy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * ����ҳ��ϵͳ�ĸ�·��������Ϊwar����ʽ����ʱ��Ӧ�õĸ�·����
 * ������Դ�http����ģ����ǲ��Ҷ�Ӧ��WebContent·����
 * ʹ��ʹ��conf.ini��Ϊ�ж�����<br>
 * ����һ��ʹ�ñ���ʱ��ϵͳ��δ��ʼ������˴����в�ʹ����־���¼��־
 * @author xujun
 * 
 */
public class FindWebRoot {

	private static Class<?> claz = FindWebRoot.class;
	private static final String CHECKFILENAME = File.separator +"WEB-INF" + File.separator +
										"conf" + File.separator + "spring-mvc.xml";

	/**
	 * ��Ϊ����Ӧ������ʱ��Ӧ�ø�·��
	 */
	private static String AppRoot;

	public static String getAppRoot() throws IOException, FileNotFoundException {

		if (AppRoot == null) {
			// ����List�洢������·��������Ѱ��������WebRoot
			List<String> relativePathList = new ArrayList<String>();
			// ��������$WEBROOTPATH/WEB-INF/libĿ¼��jar������������$WEBROOTPATH/WEB-INF/conf����������
			relativePathList.add("./../");
			// ��������$WEBROOTPATH/WEB-INF/classesĿ¼����������$WEBROOTPATH/WEB-INF/conf�����
			relativePathList.add("../../");
			// ��������$WEBROOTPATH/../libĿ¼��jar������libĿ¼��$WEBROOTPATHƽ�е��������������$WEBROOTPATH/WEB-INF/conf�����
			relativePathList.add("./WebContent/");
			relativePathList.add("./webapp/");
			// ��������$WEBROOTPATH/../classesĿ¼�У���classesĿ¼��$WEBROOTPATHƽ�е��������������$WEBROOTPATH/WEB-INF/conf�����
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
				// ��������$WEBROOTPATH/WEB-INF/classesĿ¼����������$WEBROOTPATH/WEB-INF/classes/conf�����
				path = Path.getRootPath(claz);// ������������������
				File context = new File(path + CHECKFILENAME);
				if (!context.exists()) {
					throw new IOException("�����ƻ���λ�ò���ȷ�������ҵ�Ӧ�ø�·��");
				}
			}
			AppRoot = path;
		}
		return AppRoot;
	}

	/**
	 * ���ø�·���Ĳ�����
	 * @param claz
	 */
	public static void setClaz(Class<?> claz) {
		FindWebRoot.claz = claz;
	}

}
