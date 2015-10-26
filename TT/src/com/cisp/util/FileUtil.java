package com.cisp.util;

import java.io.File;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

public class FileUtil {

	/**
	 * 2012-8-2
	 * 获取真实文件路径地址
	 * @param path
	 * @return
	 */
   public static String getFilePath(String path)
   {
	   String className = FileUtil.class.getName();
	   String classNamePath = className.replace(".", "/") + ".class";
       URL url = FileUtil.class.getClassLoader().getResource(classNamePath);

       String classPath = url.getFile();
       String[] buf = classPath.split("WebRoot");
       String  sysPath = buf[0];
       String filPath = sysPath + "WebRoot/"+path;
	   return filPath;
	
   }
   
   public static File getFile(String path)
   {
	   return new File(getFilePath(path));
   }
}
