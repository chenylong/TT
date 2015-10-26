package com.cisp.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.PropertyResourceBundle;

import org.apache.commons.lang.StringUtils;


public class Path {
	
	 private static String systemPath;
	  
	 public static File getFile(String path)
	  {
	    if (path.indexOf("..") >= 0)
	    {
	      path = replaceFileSeparator(path);
	      String[] paths = StringUtils.split(path, '/');

	      File file = new File(getSystemPath());
	      for (int i = 0; i < paths.length; ++i)
	      {
	        if (paths[i].equals(".."))
	        {
	          file = file.getParentFile();
	        }
	        else
	        {
	          file = new File(file, paths[i]);
	        }
	      }

	      return file;
	    }

	    return new File(getPath(path));
	  }
	 
	  private static String replaceFileSeparator(String path)
	  {
	    String separator = System.getProperty("file.separator");
	    if (separator.length() == 1)
	    {
	      char chr = separator.charAt(0);
	      if (chr != '/')
	      {
	        path = path.replace(chr, '/');
	      }
	    }
	    return path;
	  }
	  
	  public static String getSystemPath()
	  {
	    if (systemPath == null)
	    {
	      initializeSystemPath();
	    }

	    return systemPath;
	  }
	  
	  private static void initializeSystemPath()
	  {
	    if (systemPath == null)
	    {
	      InputStream is = ClassLoaderUtils.getClassLoader().getResourceAsStream("config.properties");
	      try
	      {
	        if (is != null)
	        {
	          PropertyResourceBundle bundle = new PropertyResourceBundle(is);
	          systemPath = bundle.getString(Application.class.getName());
	          System.out.println("load property file " + ClassLoaderUtils.getClassLoader().getResource("config.properties").getPath());
	        }
	      }
	      catch (Throwable ex)
	      {

	      }
	    }

	    if (systemPath == null)
	    {
	      try
	      {
	        systemPath = findSystemPath();
	      }
	      catch (Throwable ex)
	      {
	    	  
	      }
	    }
	  }
	  
	  private static String findSystemPath() throws Exception
	  {
	    String path = getLocation(Path.class);
	    if (path != null)
	    {
	      path = replaceFileSeparator(path);
	      path = trimFilePath(path);
	      while (path != null)
	      {
	        File folder = new File(path);
	        if (detectContext(folder))
	        {
	          if (!(path.endsWith("/")))
	          {
	            path = path + "/";
	          }
	          return path;
	        }

	        File[] files = null;//folder.listFiles(FileFilterUtils.directoryFileFilter());
	        if ((files != null) && (files.length > 0))
	        {
	          for (int i = 0; i < files.length; ++i)
	          {
	            if (detectContext(files[i]))
	            {
	              return path + "/" + files[i].getName() + "/";
	            }
	          }
	        }

	        path = trimFilePath(path);
	      }
	      return null;
	    }

	    return null;
	  }


	  public static String getLocation(Class clazz) throws Exception
	  {
	    String className = clazz.getName();
	    int pos = className.lastIndexOf(".");
	    if (pos > 0)
	    {
	      className = className.substring(pos + 1);
	    }
	    URL url = clazz.getResource(className + ".class");
	    String location = URLDecoder.decode(url.getFile(), "UTF-8");
	    int separator = location.indexOf(33);
	    if (separator >= 0)
	    {
	      if (location.startsWith("file:"))
	      {
	        location = new URL(location.substring(0, separator)).getFile();
	      }
	      else
	      {
	        location = location.substring(0, separator);
	      }
	    }
	    return new File(location).getAbsolutePath();
	  }
	  
	  private static String trimFilePath(String path)
	  {
	    int pos = path.lastIndexOf(47);
	    if (pos > 0)
	    {
	      return path.substring(0, pos);
	    }

	    return null;
	  }
	  
	  private static boolean detectContext(File folder)
	  {
	    return ((new File(folder + "/WEB-INF/web.xml").exists()) || (new File(folder, "/config/applicationContext-bean.xml").exists()));
	  }
	  
	  public static String getPath(String path)
	  {
	    return FileUtils.concatPath(getSystemPath(), path);
	  }
}
