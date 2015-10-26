package com.cisp.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class GisXmlUtil {

	public static String ListToXml(List list,String nodeName){
		
		String returnXml="";
		
		Element root=new Element("root");
		Document doc=new Document(root);

		if(list.size()>0){//有数据
			Element body=new Element("body");
			//Element nodes=new Element(nodesName);
			
			Map<String,Object> map=new HashMap<String, Object>();
			
			try{
				for(int i=0;i<list.size();i++)
				{
					map=(Map<String, Object>)list.get(i);
				    
					Iterator it=map.entrySet().iterator();
					Element node=new Element(nodeName);
					while(it.hasNext())
					{   
						Entry entry=(Entry)it.next();
						String key=(String)entry.getKey();
						Element e=null;
						Object obj=null;
						if(key.toLowerCase().equals("rrarray"))
						{
							e=new Element(key.toLowerCase());
							Element eTemp=new Element("rrid");
							obj=entry.getValue();
							String value="";
							if(obj!=null) value=obj.toString();
							eTemp.setText(value.toLowerCase());
							e.addContent(eTemp);
						}
						if(key.toLowerCase().equals("woarray"))
						{
							e=new Element(key.toLowerCase());
							
							obj=entry.getValue();
							String value="";
							if(obj!=null) value=obj.toString();
							String[] strArray=value.split(",");
							for(int m=0;m<strArray.length;m++)
							{
								Element eTemp=new Element("woid");
								eTemp.setText(strArray[m].toLowerCase());
							    e.addContent(eTemp);
							}
						}
						else
						{
							 e=new Element(key.toLowerCase());
							 obj=entry.getValue();
							String value="";
							if(obj!=null) value=obj.toString();
							e.setText(value.toLowerCase());
						}
						node.addContent(e);
					}
					body.addContent(node);
				}
				//body.addContent(nodes);
				root.addContent(GisXmlUtil.CreatSuccessHeadElement());
				root.addContent(body);
				XMLOutputter xmlopt=new XMLOutputter();
				returnXml=xmlopt.outputString(doc);
			}
			catch(Exception ex)
			{
				System.out.println(ex.getLocalizedMessage());
			}
			
		}
		
		return  returnXml;
	}
	
	

	public static Element CreateHeadElement(String code,String message){
		Element headElement=new Element("head");
		Element codeElement=new Element("code");
		codeElement.setText(code);
		Element messageElement=new Element("message");
		messageElement.setText(message);
		headElement.addContent(codeElement);
		headElement.addContent(messageElement);
		return  headElement;
	}
	
	public static Element CreatSuccessHeadElement(){
		return  CreateHeadElement("1","成功");
	}
	
	public static String CreateNodeNotFoundString(String message){
       String returnXml="";
		Element root=new Element("root");
		Document doc=new Document(root);
        root.addContent(CreateHeadElement("2",message));
        XMLOutputter xmlopt=new XMLOutputter();
		returnXml=xmlopt.outputString(doc);
	   return returnXml ;

	}
	
	public static String CreateBadXmlString(String message){
	       String returnXml="";
			Element root=new Element("root");
			Document doc=new Document(root);
	        root.addContent(CreateHeadElement("2",message));
	        XMLOutputter xmlopt=new XMLOutputter();
			returnXml=xmlopt.outputString(doc);
		   return returnXml ;

		}
	
	public static String CreateUnknowExceptionString(String message){
	       String returnXml="";
			Element root=new Element("root");
			Document doc=new Document(root);
	        root.addContent(CreateHeadElement("2",message));
	        XMLOutputter xmlopt=new XMLOutputter();
			returnXml=xmlopt.outputString(doc);
		   return returnXml ;

		}
	
	public static Element CreatExceptionHeadElement(){
		return  CreateHeadElement("3","出现异常");
	}
	
	public static Document StringToXml(String xml) throws Exception
	{
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;

		try {
			doc = sb.build(new StringReader(xml));
		}
		catch(Exception ex){
			
		}
		return doc;
	}
	
	public static HashMap GetMap(String xmlStr)
	{
		HashMap  map = new HashMap();
			try
			{

				String str = GetValue(StringToXml(xmlStr),"WOID");
				map.put("WOID",str);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return map;
	}
	public static String GetValue(Document doc,String name) throws Exception{
		String value="";
		Element root=doc.getRootElement();
		
		if(root.getName().equals("root"))
		{
			Element body=root.getChild("body");
			Element e=body.getChild("WOID");
			if(e!=null)
			{
			value=e.getText();
			}
			else{
				
			}
			/*
			List list=body.getChildren();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String key = e.getName();
				
				if (key == name) {
					value=e.getText();
				}
			}
			*/
		}
		return value;
	}
	
	public static List GetList(Document doc,String name) throws Exception{
		List list=new ArrayList();
		Element root=doc.getRootElement();
		
		if(root.getName().equals("root"))
		{
			Element body=root.getChild("body");
			if(body!=null)
			{  
			   list=body.getChildren(name);
			}
			else{
				
			}
		}
		return list;
	}
	
	public static Element GetElement(Document doc,String name) throws Exception{
		Element retElement=null;
		Element root=doc.getRootElement();
		
		if(root.getName().equals("root"))
		{
			Element body=root.getChild("body");
			if(body!=null)
			{  
				retElement=body.getChild(name);
			}
			else{
				
			}
		}
		return retElement;
	}
}
