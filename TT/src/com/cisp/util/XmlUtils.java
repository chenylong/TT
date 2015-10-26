package com.cisp.util;


import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Date;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

public class XmlUtils {

	    public static String getXmlStr(String jsonStr) {   
	    	
	      JSON json = JSONSerializer.toJSON(jsonStr);  
	      
	      XMLSerializer xmlSerializer = new XMLSerializer();  
	      
	      xmlSerializer.setTypeHintsEnabled(false);
	      
	      xmlSerializer.setTypeHintsCompatibility(true);
	      
	      xmlSerializer.setRootName("rowset");
	      
	      xmlSerializer.setElementName("row");
	      
	      String xml = xmlSerializer.write(json);   
	      
	      return xml;  
	  }  
	  public static String json2xml(String jsonString){  
	    	 XMLSerializer xmlSerializer = new XMLSerializer();  
	    	 //return xmlSerializer.write(JSONSerializer.toJSON(jsonString));  
	    	 return xmlSerializer.write(JSONArray.fromObject(jsonString));//这种方式只支持JSON数组  
	  } 
	  

	  public static Document createDocument()
	  {
	    return DocumentHelper.createDocument();
	  }

	  public static Document createDocument(String xml) throws DocumentException
	  {
	    return createDocument(new StringReader(xml));
	  }

	  public static Document createDocument(Reader reader) throws DocumentException
	  {
	    return createDocument(reader, false, null);
	  }

	  public static Document createDocument(Reader reader, boolean validate, String systemId) throws DocumentException
	  {
	    SAXReader saxreader = new SAXReader(validate);
	    return saxreader.read(reader, systemId);
	  }

	  public static Document createDocument(InputStream in, boolean validate, String systemId) throws DocumentException
	  {
	    SAXReader saxreader = new SAXReader(validate);
	    return saxreader.read(in, systemId);
	  }

	  public static Document createDocument(URL url, boolean validate) throws DocumentException
	  {
	    SAXReader saxreader = new SAXReader(validate);
	    return saxreader.read(url);
	  }

	  public static Document createDocument(InputSource in, boolean validate) throws DocumentException
	  {
	    SAXReader saxreader = new SAXReader(validate);
	    return saxreader.read(in);
	  }

	  public static Document createDocument(File file, boolean validate) throws DocumentException
	  {
	    SAXReader saxreader = new SAXReader(validate);
	    return saxreader.read(file);
	  }

	  public static void addAttribute(Element element, String name, Object value)
	  {
	    if (value != null)
	    {
	      addAttribute(element, name, value.toString());
	    }
	  }

	  public static void addAttribute(Element element, String name, Date value)
	  {
	    if (value != null)
	    {
	      addAttribute(element, name, DateUtil.formatDate(value.getTime()));
	    }
	  }

	  public static void addAttribute(Element element, String name, int value)
	  {
	    addAttribute(element, name, String.valueOf(value));
	  }

	  public static void addAttribute(Element element, String name, boolean value)
	  {
	    addAttribute(element, name, String.valueOf(value));
	  }

	  public static void addAttribute(Element element, String name, boolean value, boolean ignore)
	  {
	    if (ignore != value)
	    {
	      addAttribute(element, name, String.valueOf(value));
	    }
	  }

	  public static void addAttribute(Element element, String name, double value)
	  {
	    addAttribute(element, name, String.valueOf(value));
	  }

	  public static void addAttribute(Element element, String name, long value)
	  {
	    addAttribute(element, name, String.valueOf(value));
	  }

	  public static void addAttribute(Element element, String name, String value)
	  {
	    if ((element != null) && (name != null) && (value != null))
	    {
	      element.addAttribute(name, value);
	    }
	  }

	  public static int getAttribute(Element element, String name, int defaultValue)
	  {
	    return StringUtil.getInteger(element.attributeValue(name), defaultValue);
	  }

	  public static long getAttribute(Element element, String name, long defaultValue)
	  {
	    return StringUtil.getLong(element.attributeValue(name), defaultValue);
	  }

	  public static String getAttribute(Element element, String name, String defaultValue)
	  {
	    return StringUtil.getString(element.attributeValue(name), defaultValue);
	  }

	  public static double getAttribute(Element element, String name, double defaultValue)
	  {
	    return StringUtil.getDouble(element.attributeValue(name), defaultValue);
	  }

	  public static boolean getAttribute(Element element, String name, boolean defaultValue)
	  {
	    return StringUtil.getBoolean(element.attributeValue(name), defaultValue);
	  }

	  public static Element addElement(Branch parent, String name)
	  {
	    return parent.addElement(name);
	  }

	  public static Element addElement(Branch parent, String name, Object value)
	  {
	    return addElement(parent, name, (value == null) ? "" : value.toString());
	  }

	  public static Element addElement(Branch parent, String name, String value)
	  {
	    if ((name != null) && (!(name.equals(""))) && (value != null))
	    {
	      Element element = parent.addElement(name);
	      element.setText(value);
	      return element;
	    }

	    return null;
	  }

	  public static void setText(Branch parent, String value)
	  {
	    if (value != null)
	    {
	      parent.setText(value);
	    }
	  }

	  public static void setCDATA(Element parent, Object value)
	  {
	    if (value != null)
	    {
	      parent.addCDATA(value.toString());
	    }
	  }

	  public static void setCDATA(Element parent, byte[] value)
	  {
	    if (value != null)
	    {
	      parent.addCDATA(new String(value));
	    }
	  }

	  public static void setCDATA(Element parent, String value)
	  {
	    if (value != null)
	    {
	      parent.addCDATA(value);
	    }
	  }

	  public static void setCDATA(Branch parent, String name, String value)
	  {
	    if (value != null)
	    {
	      Element element = parent.addElement(name);
	      element.addCDATA(value);
	    }
	  }

	  public static String print(Document document) throws IOException
	  {
	    return print(document, "UTF-8", true);
	  }

	  public static String print(Document document, String encoding, boolean suppressDeclaration) throws IOException
	  {
	    StringWriter writer = new StringWriter();
	    try
	    {
	      print(document, writer, encoding, suppressDeclaration);
	      String str = writer.toString();

	      return str; } finally { writer.close();
	    }
	  }

	  public static void print(Document document, Writer out) throws IOException
	  {
	    print(document, out, "UTF-8", true);
	  }

	  public static void print(Document document, Writer out, String encoding, boolean suppressDeclaration) throws IOException
	  {
	    OutputFormat format = OutputFormat.createPrettyPrint();
	    format.setEncoding(encoding);
	    format.setIndent("\t");
	    format.setSuppressDeclaration(suppressDeclaration);
	    print(document, out, format);
	  }

	  public static void print(Document document, Writer out, OutputFormat format) throws IOException
	  {
	    XMLWriter writer = new XMLWriter(out, format);
	    writer.write(document);
	  }

}
