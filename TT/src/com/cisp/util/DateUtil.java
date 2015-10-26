package com.cisp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang.time.DateUtils;

public class DateUtil extends DateUtils{

  public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";
  public static final String DEFAULT_FORMAT_TIME = "HH:mm:ss.SSS";
  public static final String DEFAULT_FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static String formatDate(long time)
  {
    return formatDate(time, "yyyy-MM-dd HH:mm:ss");
  }

  public static Date parseDate(String time)
  {
    return parseDate(time, "yyyy-MM-dd HH:mm:ss");
  }

  public static Date parseDate(String time, String format)
  {
    return parseDate(time, format, Locale.getDefault());
  }

  public static Date parseDate(String time, String format, Locale locale)
  {
    if (format == null)
    {
      format = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
    try
    {
      return sdf.parse(time);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
  }

  public static String formatDate(long time, String format)
  {
    return formatDate(time, format, Locale.getDefault());
  }

  public static String formatDate(long time, String format, Locale locale)
  {
    Date date = new Date(time);
    if (format == null)
    {
      format = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
    return sdf.format(date);
  }

}
