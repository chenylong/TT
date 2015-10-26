package com.cisp.util;

import java.util.HashMap;
import java.util.Map;

public class ClassLoaderUtils {
	  private static Map primitiveMapping = new HashMap();

	  public static ClassLoader getClassLoader()
	  {
	    return Thread.currentThread().getContextClassLoader();
	  }

	  public static Class loadClass(String className) throws Exception
	  {
	    return loadClass(className, null);
	  }

	  public static Class loadClass(String className, Class defaultClass) throws Exception
	  {
	    if ((className == null) || (className.equals("")))
	    {
	      return defaultClass;
	    }

	    if (primitiveMapping.containsKey(className))
	    {
	      return ((Class)primitiveMapping.get(className));
	    }

	    try
	    {
	      return getClassLoader().loadClass(className);
	    }
	    catch (ClassNotFoundException ex) {
	    }
	    return Class.forName(className);
	  }

	  static
	  {
	    primitiveMapping.put(Boolean.TYPE.getName(), Boolean.TYPE);
	    primitiveMapping.put(Character.TYPE.getName(), Character.TYPE);
	    primitiveMapping.put(Byte.TYPE.getName(), Byte.TYPE);
	    primitiveMapping.put(Short.TYPE.getName(), Short.TYPE);
	    primitiveMapping.put(Integer.TYPE.getName(), Integer.TYPE);
	    primitiveMapping.put(Long.TYPE.getName(), Long.TYPE);
	    primitiveMapping.put(Float.TYPE.getName(), Float.TYPE);
	    primitiveMapping.put(Double.TYPE.getName(), Double.TYPE);
	    primitiveMapping.put(Void.TYPE.getName(), Void.TYPE);
	  }
}
