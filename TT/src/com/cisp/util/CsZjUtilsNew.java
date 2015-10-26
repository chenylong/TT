package com.cisp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CsZjUtilsNew {

	public static final String WEBSERVICE_DTD_URL="/WEB-INF/config/zepcis_interface_1_0.dtd";
	
	
	public static HashMap readXml(String strPara)
	{
		HashMap map =new HashMap();
		try 
		{
			SAXReader saxreader = new SAXReader();
			saxreader.setValidation(false);
			saxreader.setEntityResolver(new EntityResolver()
			{

				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
				{
					InputStream stream = new FileInputStream(Path.getSystemPath()+WEBSERVICE_DTD_URL);
	                InputSource is = new InputSource(stream);
	                is.setPublicId(publicId);
	                is.setSystemId(systemId);
	                return is;
				}
			});   
			Document document = saxreader.read(new StringReader(strPara));  
			Element element=document.getRootElement();
			readMap(element.selectNodes("//R/C"),map);
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		return map;
	}
	public static HashMap readXmlGIS(String strPara)
	{
		HashMap map =new HashMap();

		SAXReader saxreader = new SAXReader();
		saxreader.setValidation(false);
		Document document;
		try
		{
			document = saxreader.read(new StringReader(strPara));
			Element element=document.getRootElement();
		}
		catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return map;
	}
	
	public static String writeXml(HashMap map)
	{
		Document document = XmlUtils.createDocument();
		Element dbsetElement = XmlUtils.addElement(document, "DBSET");
		if(!map.isEmpty())
		{
			Element rowElement = XmlUtils.addElement(dbsetElement, "R");
			writeMap(map,rowElement);
		}
		try 
		{
			String xmlStr = XmlUtils.print(document);
			return xmlStr;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	private static void writeMap(HashMap map, Element rowElement)
	{
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext())
		{
			String strName = (String) iter.next();
			Object strValue = map.get(strName);
			Element parameterElement = XmlUtils.addElement(rowElement, "C");
			XmlUtils.addAttribute(parameterElement, "N", strName);
			XmlUtils.setText(parameterElement, getString(strValue));
		}
	}
	private static void writeMapGIS(HashMap map, Element rowElement)
	{
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext())
		{
			String strName = (String) iter.next();
			Object strValue = map.get(strName);
			Element parameterElement = XmlUtils.addElement(rowElement, null);
			XmlUtils.addAttribute(parameterElement, null, strName);
			XmlUtils.setText(parameterElement, getString(strValue));
		}
	}


	public static String getString(Object value)
	{
		if (value == null)
		{
			return "";
		}

		if (value instanceof String)
		{
			return (String) value;
		}

		if (value instanceof java.util.Date)
		{
			return String.valueOf(((java.util.Date) value).getTime());
		}

		return value.toString();
	}
	public static String getStringNew(Object value)
	{
		if (value == null)
		{
			return "";
		}

		if (value instanceof String)
		{
			return (String) value;
		}

		if (value instanceof java.util.Date)
		{
			String TimeValue = ((Timestamp) value).toString().substring(0, 19);
			return TimeValue;
		}

		return value.toString();
	}
	private static void readMap(List elements,HashMap map)
	{
		for(int i=0, size = elements.size();i < size; i++)
		{
			Element element = (Element) elements.get(i);
			String strName = XmlUtils.getAttribute(element, "N", null);
			String strValue = element.getText();
			map.put(strName, strValue);
		}
	}

}
