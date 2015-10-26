package com.cisp.util;

public class FileUtils extends org.apache.commons.io.FileUtils{

	 private static String PATH_SEPARATOR = "/";
	  
	 public static final String ENCODING = System.getProperty("file.encoding");
	  
	 public static final String concatPath(String parent, String child)
	  {
	    if ((parent == null) || (parent.trim().length() <= 0))
	    {
	      return child;
	    }

	    if ((child == null) || (child.trim().length() <= 0))
	    {
	      return parent;
	    }

	    if (!(parent.endsWith(PATH_SEPARATOR)))
	    {
	      if (!(child.startsWith(PATH_SEPARATOR)))
	      {
	        parent = parent + PATH_SEPARATOR;
	      }

	    }
	    else if (child.startsWith(PATH_SEPARATOR))
	    {
	      child = child.substring(1);
	    }

	    return parent.concat(child);
	  }
}
