package com.cisp.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtils {
	  
	  public static String getAddress()
	  {
	    try
	    {
	      return InetAddress.getLocalHost().getHostAddress();
	    }
	    catch (Exception ex) {
	    }
	    return "localhost";
	  }

	  public static String getServerName()
	  {
	    try {
			return InetAddress.getLocalHost().getHostName().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    return "";
	  }

	  public static String getDomainDir()
	  {
	    return System.getProperty("user.dir");
	  }
}
