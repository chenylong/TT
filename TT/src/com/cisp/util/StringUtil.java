package com.cisp.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {
	
	  public static String formatMessage(String messagePattern, Object[] args)
	  {
	    if ((args == null) || (args.length == 0))
	    {
	      return messagePattern;
	    }

	    if (messagePattern == null)
	    {
	      return null;
	    }

	    int i = 0;
	    int len = messagePattern.length();
	    int j = messagePattern.indexOf(123);

	    StringBuffer sbuf = new StringBuffer(messagePattern.length() + 50);

	    for (int k = 0; k < args.length; ++k)
	    {
	      char escape = 'x';

	      j = messagePattern.indexOf(123, i);

	      if ((j == -1) || (j + 1 == len))
	      {
	        if (i == 0)
	        {
	          return messagePattern;
	        }

	        sbuf.append(messagePattern.substring(i, messagePattern.length()));
	        return sbuf.toString();
	      }

	      char delimStop = messagePattern.charAt(j + 1);
	      if (j > 0)
	      {
	        escape = messagePattern.charAt(j - 1);
	      }

	      if (escape == '\\')
	      {
	        --k;
	        sbuf.append(messagePattern.substring(i, j - 1));
	        sbuf.append('{');
	        i = j + 1;
	      } else {
	        if (delimStop != '}')
	        {
	          sbuf.append(messagePattern.substring(i, messagePattern.length()));
	          return sbuf.toString();
	        }

	        sbuf.append(messagePattern.substring(i, j));
	        sbuf.append(args[k]);
	        i = j + 2;
	      }
	    }

	    sbuf.append(messagePattern.substring(i, messagePattern.length()));
	    return sbuf.toString();
	  }

	  public static String printException(Throwable exception)
	  {
	    StringWriter sw = new StringWriter();
	    PrintWriter writer = new PrintWriter(sw);
	    try
	    {
	      exception.printStackTrace(writer);
	      String str = sw.getBuffer().toString();

	      return str; } finally { writer.close();
	    }
	  }

	  public static String escapeSqlLike(String value, String escape)
	  {
	    if ((escape != null) && (value != null))
	    {
	      value = replace(value, escape, escape + escape);
	      value = replace(value, "_", escape + "_");
	      value = replace(value, "%", escape + "%");
	      value = replace(value, "?", "_");
	      value = replace(value, "*", "%");
	    }

	    return value;
	  }

	  public static String escapeSqlWhere(String condition, String escape)
	  {
	    if (escape != null)
	    {
	      return condition + " escape '" + escape + "'";
	    }

	    return condition;
	  }



	  public static String getString(Object value, String defaultValue)
	  {
	    if (value == null)
	    {
	      return defaultValue;
	    }

	    if (value instanceof String)
	    {
	      return ((String)value);
	    }

	    return value.toString();
	  }

	  public static int getInteger(Object value, int defaultValue)
	  {
	    if (value == null)
	    {
	      return defaultValue;
	    }

	    if (value instanceof Number)
	    {
	      return ((Number)value).intValue();
	    }
	    if (value instanceof String)
	    {
	      String stringValue = (String)value;
	      return ((stringValue.equals("")) ? defaultValue : Integer.parseInt(stringValue));
	    }

	    return defaultValue;
	  }

	  public static long getLong(Object value, long defaultValue)
	  {
	    if (value == null)
	    {
	      return defaultValue;
	    }

	    if (value instanceof Number)
	    {
	      return ((Number)value).longValue();
	    }
	    if (value instanceof String)
	    {
	      String stringValue = (String)value;
	      return ((stringValue.equals("")) ? defaultValue : Long.parseLong(stringValue));
	    }

	    return defaultValue;
	  }

	  public static double getDouble(Object value, double defaultValue)
	  {
	    if (value == null)
	    {
	      return defaultValue;
	    }

	    if (value instanceof Number)
	    {
	      return ((Number)value).doubleValue();
	    }
	    if (value instanceof String)
	    {
	      String stringValue = (String)value;
	      return ((stringValue.equals("")) ? defaultValue : Double.parseDouble(stringValue));
	    }

	    return defaultValue;
	  }

	  public static boolean getBoolean(Object value, boolean defaultValue)
	  {
	    if (value == null)
	    {
	      return defaultValue;
	    }

	    if (value instanceof Boolean)
	    {
	      return ((Boolean)value).booleanValue();
	    }
	    if (value instanceof String)
	    {
	      String stringValue = (String)value;
	      return ((stringValue.equals("")) ? defaultValue : stringValue.equalsIgnoreCase(Boolean.TRUE.toString()));
	    }
	    if (value instanceof Number)
	    {
	      return (((Number)value).intValue() > 0);
	    }

	    return defaultValue;
	  }

	  public static boolean hasLength(String str)
	  {
	    return ((str != null) && (str.length() > 0));
	  }

	  public static String substring(String name, String beginDivsion, String endDivsion)
	  {
	    int pos1 = name.indexOf(beginDivsion);
	    int pos2 = name.indexOf(endDivsion, pos1 + 1);
	    if ((pos1 > 0) && (pos2 > 0))
	    {
	      return name.substring(pos1 + 1, pos2);
	    }

	    return name;
	  }

	  public static String subBytes(String s, int length)
	  {
	    try
	    {
	      if (s == null)
	      {
	        return null;
	      }
	      byte[] bytes = s.getBytes("unicode");
	      int n = 0;
	      int i = 2;
	      for (; (i < bytes.length) && (n < length); ++i)
	      {
	        if (i % 2 == 1)
	        {
	          ++n;
	        }
	        else if (bytes[i] != 0)
	        {
	          ++n;
	        }
	      }

	      if (i % 2 == 1)
	      {
	        if (bytes[(i - 1)] != 0)
	        {
	          i -= 1;
	        }
	        else
	        {
	          i += 1;
	        }
	      }
	      return new String(bytes, 0, i, "unicode");
	    }
	    catch (UnsupportedEncodingException e)
	    {
	      e.printStackTrace(); }
	    return null;
	  }

	  public static String[] trim(String[] array)
	  {
	    for (int i = 0; i < array.length; ++i)
	    {
	      if (array[i] != null)
	      {
	        array[i] = array[i].trim();
	      }
	    }

	    return array;
	  }

}
