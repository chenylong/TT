package com.cisp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * geyj
 * 2012-8-31
 * 读取Properties文件内容
 * config 为Properties文件，如config.Properties
 * key    为Properties文件的键值
 */
public class PropertiesUtils {

	public static String getPropertyValue(String configFile,String key)
	{
		
		Properties config = null;
		InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(configFile);
		config = new Properties();
		String value = null;
		try{
			config.load(in);
			in.close();
			value = config.getProperty(key);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
}
