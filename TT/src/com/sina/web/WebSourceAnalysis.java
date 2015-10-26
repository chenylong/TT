package com.sina.web;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;  
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.net.HttpURLConnection;  
import java.net.URL; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sina.util.ImageTool;
public class WebSourceAnalysis {
	
	  public static void main(String[] args) throws Exception    {  
	       // URL url = new URL("http://cl.clvv.biz/htm_data/7/1510/1663566.html");   
	      URL url = new URL("http://cl.clvv.biz/htm_data/7/1510/1690812.html"); 
		  
		  // String urlsource = getURLSource(url);  
	       // System.out.println(urlsource);  
	        
	      ArrayList alist =  getURLImageArray(url);
	      System.out.println(alist);
	        
	        
	        String mode = "[a-zA-Z]+://[^\\s]*jpg";		    
		      Pattern exp = Pattern.compile(mode,Pattern.CASE_INSENSITIVE ); 
		      
	      String s = "  img src=\"http://img.135q.com/2015-03/28/14275311220009.jpg\" width=\"185\"";
	      Matcher m = exp.matcher(s);
	      if(m.find()){
	    	  
	    	  //System.out.println(m);
	    	  System.out.println(m.group());
	    	  
	      }else{
	    	  
	    	  System.out.println(m);
	    	  
	      }
	      
	      
	     String filename,filetype;
	      
	        for (int i = 0; i < alist.size(); i++) {
	        	
	        	 Matcher mm = exp.matcher(alist.get(i).toString());
	        	 while (mm.find()) {
	        		 System.out.println("find...");
	        		 String addr = mm.group().replace("\"", "").replace("\'","");
	        		 
	        		 System.out.println(addr);
	        		 
	        		    String jpgaddr [] = addr.split("/");
	        			System.out.println(Arrays.asList(jpgaddr));
	        			
	        			String jpgname = jpgaddr[jpgaddr.length-1];
	        			
	        			filename = jpgname.split("\\.")[0];
	        			filetype = jpgname.split("\\.")[1];
	        			//insertImageByteaByBase64(getImageDateByUrl(url),filename,filetype);
	        			ImageTool tt = new ImageTool();
	        			//tt.imageSaveByUrl(addr,filename,filetype);
	        			byte []data = tt.getImageDateByUrl(addr);
	        			tt.insertImageByteaByBase64(data,filename,filetype,addr);
	        	 }
	        	 
	        	// System.out.println(alist.get(i)); 
				
			}
//	        
    
	    }  
	      
	    /** *//** 
	     * 通过网站域名URL获取该网站的源码 
	     * @param url 
	     * @return String 
	     * @throws Exception 
	     */  
	    public static String getURLSource(URL url) throws Exception    {  
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        conn.setRequestMethod("GET"); 
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
           // conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
	        conn.setConnectTimeout(100 * 1000);  
	        InputStream inStream =  conn.getInputStream();  //通过输入流获取html二进制数据  
	        byte[] data = readInputStream(inStream);        //把二进制数据转化为byte字节数据 
//	        
//	        StringBuffer sb = new StringBuffer();
//
//	        BufferedReader br = new BufferedReader(new InputStreamReader(inStream,"UTF-8"));
//	        String line;
//	        while((line=br.readLine())!=null){
//	        sb.append(line);
//	        }
	       String htmlSource = new String(data,"gbk"); 
	        
	        inStream.close();
	        conn.disconnect();
	        return htmlSource;  
	    }  
	    
	    public static ArrayList getURLImageArray(URL url) throws Exception    {  
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        conn.setRequestMethod("GET"); 
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
           // conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
	        conn.setConnectTimeout(100 * 1000);  
	        InputStream inStream =  conn.getInputStream();  //通过输入流获取html二进制数据  
	      //  byte[] data = readInputStream(inStream);        //把二进制数据转化为byte字节数据/ 
///	        
	        StringBuffer sb = new StringBuffer();
            ArrayList alist = new ArrayList();
	        BufferedReader br = new BufferedReader(new InputStreamReader(inStream,"GBK"));
	        String line;
	        while((line=br.readLine())!=null){
	        sb.append(line);
	        if(line.indexOf("<img src=")>-1 || line.indexOf("<input src=")>-1){
	        	
	        	alist.add(line);
	        }
	        
	        }
//	       String htmlSource = new String(data,"gbk"); 
//	        
	        inStream.close();
	        conn.disconnect();
	        return alist;  
	    }  
	      
	    /** *//** 
	     * 把二进制流转化为byte字节数组 
	     * @param instream 
	     * @return byte[] 
	     * @throws Exception 
	     */  
	    public static byte[] readInputStream(InputStream instream) throws Exception {  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[]  buffer = new byte[1204];  
	        int len = 0;  
	        while ((len = instream.read(buffer)) != -1){  
	            outStream.write(buffer,0,len);  
	        }  
	        instream.close();  
	        return outStream.toByteArray();           
	    }
	    
	    /**
	     * 
	     * 
	     * 
	     */
	    

}
