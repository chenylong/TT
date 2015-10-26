package com.cisp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.AccessController;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import sun.security.action.GetPropertyAction;

public class CsvWriter {
	 public static final char delimiter = 44;
	  public static final char quote = 34;
	  public static final String lineSeparator = (String)AccessController.doPrivileged(new GetPropertyAction("line.separator"));

	  public static void write(String[][] data, Writer writer)
	  {
	    int i;
	    try
	    {
	      for (i = 0; i < data.length; ++i)
	      {
	        if (i > 0)
	        {
	          writer.write(lineSeparator);
	        }

	        write(data[i], writer);
	      }
	    }
	    catch (IOException ex)
	    {
	    	
	    }
	  }

	  public static void write(String[] data, Writer writer)
	  {
	    int i;
	    try {
	      for (i = 0; i < data.length; ++i)
	      {
	        if (i > 0)
	        {
	          writer.write(44);
	        }

	        if (data[i] != null && !("null".equals(data[i])))
	        {
	          if (((data[i].indexOf(44) >= 0) || (data[i].indexOf(10) >= 0)))
	          {
	            writer.write(34);

	            char[] chars = data[i].toCharArray();
	            for (int j = 0; j < chars.length; ++j)
	            {
	              writer.write(chars[j]);
	              if (chars[j] == '"')
	              {
	                writer.write(34);
	              }
	            }
	            writer.write(34);
	          }
	          else
	          {
	        	SimpleDateFormat sf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	            try{
        		Date date = new Date(Long.parseLong(data[i]));  
				String strDate = sf.format(date).toString();
				if(strDate.substring(0, 10).equals("1970-01-01"))
				{
					writer.write(data[i]);	
				}
				else if(strDate.substring(0, 10).compareTo("1970-01-01") >0 && "2011-01-01".compareTo(strDate.substring(0, 10)) > 0)
				{
					writer.write(data[i]);	
				}
				else if(strDate.substring(0, 10).compareTo("2099-12-31") > 0)
				{
					writer.write(data[i]);	
				}
				else writer.write(strDate);	
	            }catch(Exception e)
	            {
	            	writer.write(data[i]);	
	            }
	          }

	        }
	        else
	          writer.write("");

	      }

	      writer.write("\n");
	    }
	    catch (IOException ex)
	    {
	    	
	    }
	  }

	  public static void write(String[][] data, File file)
	  {
	    Writer writer = null;
	    FileOutputStream fis = null;
	    try
	    {
	      fis = new FileOutputStream(file);
	      writer = new OutputStreamWriter(fis);

	      write(data, writer);
	    }
	    catch (IOException ex)
	    {
	    }
	    finally
	    {
	      try
	      {
	        if (writer != null)
	        {
	          writer.close();
	          writer = null;
	        }

	        if (fis != null)
	        {
	          fis.close();
	          fis = null;
	        }
	      }
	      catch (IOException ex)
	      {
	      }
	    }
	  }

	  public static void main(String[] args)
	  {
	    Writer writer = null;
	    FileOutputStream fis = null;
	    try
	    {
	      File file = new File("E:/temporary/csv1.txt");
	      fis = new FileOutputStream(file);
	      writer = new OutputStreamWriter(fis);

	      String[] value1 = { "11", ",data", "11-11-11", "myname" };
	      String[] value2 = { "11", "\"my,\"\",data", "11-11-11", "中文" };

	      String[][] value = { value1, value2 };
	      write(value, writer);
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    finally
	    {
	      if (writer != null)
	      {
	        try
	        {
	          writer.close();
	          writer = null;
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	        }
	      }

	      if (fis != null)
	      {
	        try
	        {
	          fis.close();
	          fis = null;
	        }
	        catch (Exception e)
	        {
	          e.printStackTrace();
	        }
	      }
	    }
	  }
}
