package com.sfpy.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.StringTokenizer;

public class Path {
	public static String getPathFromClass(Class cls) throws IOException {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			path = file.getCanonicalPath();
		}
		return path;
	}

	public static String getFullPathRelateClass(String relatedPath, Class cls) throws IOException {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		path = file.getCanonicalPath();
		return path;
	}

	private static URL getClassLocationURL(Class cls) {
		if (cls == null) {
			throw new IllegalArgumentException("null input: cls");
		}
		URL result = null;
		String clsAsResource = cls.getName().replace('.', '/').concat(".class");
		ProtectionDomain pd = cls.getProtectionDomain();
		if (pd != null) {
			CodeSource cs = pd.getCodeSource();
			if (cs != null) {
				result = cs.getLocation();
			}
			try {
				String temp = result.getFile();
				while (temp.indexOf("%20") > 0) {
					int n = temp.indexOf("%20");
					temp = temp.substring(0, n) + " " + temp.substring(n + 3);
				}
				result = new URL(result.getProtocol() + ":" + temp);
			} catch (MalformedURLException ignore) {
			}
			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if ((result.toExternalForm().endsWith(".jar")) || (result.toExternalForm().endsWith(".zip"))) {
							result = new URL("jar:".concat(result.toExternalForm()).concat("!/").concat(clsAsResource));
						} else if (new File(result.getFile()).isDirectory()) {
							result = new URL(result, clsAsResource);
						}
					} catch (MalformedURLException ignore) {
					}
				}
			}
		}
		if (result == null) {
			ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}

	public static String getRootPath(Class cls) throws IOException {
		String relative = "";
		String path = getPathFromClass(cls);
		if (path.lastIndexOf("jar") == path.length() - 3) {
			File jarFile = new File(path);

			return jarFile.getParent();
		}
		String packagename = cls.getPackage().getName();
		StringTokenizer tokenizer = new StringTokenizer(packagename, ".", false);
		int num = tokenizer.countTokens() + 1;
		for (int i = 0; i < num - 1; i++) {
			relative = relative + "../";
		}
		path = getFullPathRelateClass(relative, cls);

		return path;
	}

	public static String getRootRelativePath(String relativePath, Class cls) throws IOException {
		String path = null;
		if (relativePath == null) {
			throw new NullPointerException();
		}
		String rootPath = System.getProperty("KOAL_CLS_LOCATION_" + cls.getName());
		if (rootPath == null) {
			rootPath = getRootPath(cls);
		}
		String tempPath = rootPath + File.separator + relativePath;

		File file = new File(tempPath);
		path = file.getCanonicalPath();

		path = URLDecoder.decode(path, "UTF-8");
		return path;
	}
}
