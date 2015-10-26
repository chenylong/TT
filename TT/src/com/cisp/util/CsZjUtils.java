package com.cisp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
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


public class CsZjUtils {

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
			readMap(element.selectNodes("//ROW/COL"),map);
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
		}
		return map;
	}
	

	

	

	
	private static void readMap(List elements,HashMap map)
	{
		for(int i=0, size = elements.size();i < size; i++)
		{
			Element element = (Element) elements.get(i);
			String strName = XmlUtils.getAttribute(element, "NAME", null);
			String strValue = element.getText();
			map.put(strName, strValue);
		}
	}
	

	
	public static String writeXml(HashMap map)
	{
		Document document = XmlUtils.createDocument();
		Element dbsetElement = XmlUtils.addElement(document, "DBSET");
		if(!map.isEmpty())
		{
			Element rowElement = XmlUtils.addElement(dbsetElement, "ROW");
			writeMap(map,rowElement);
		}
		try 
		{
			String xmlStr = XmlUtils.print(document);
			return xmlStr.replaceAll("\n*\t*", "");
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
			Element parameterElement = XmlUtils.addElement(rowElement, "COL");
			XmlUtils.addAttribute(parameterElement, "NAME", strName);
			XmlUtils.setText(parameterElement, getString(strValue));
		}
	}
	

	
	private static String getString(Object value)
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
	
	public static int getRowCount(String strXml)
	{
		int iCount = 0;
		try 
		{
			Document document = XmlUtils.createDocument(strXml);
			Element element=document.getRootElement();
			List elements = element.selectNodes("//ROW");
			iCount = elements.size();
			return iCount ;
		} 
		catch (DocumentException e) 
		{
			e.printStackTrace();
			return iCount;
		}
	}

}
