package com.sina.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.postgresql.util.Base64;

public class ImageTool {
	
	public static void main(String args[]){
		
		String url = "http://cl.clvv.biz/htm_data/7/1510/1690812.html";
		String filename = "test2";
		String filetype = "jpg";
		
		String temp[] = read(url);
		
		System.out.println(Arrays.asList(temp));
		
		String addr = temp[2];

		String jpgaddr [] = addr.split("/");
		System.out.println(Arrays.asList(jpgaddr));
		
		String jpgname = jpgaddr[jpgaddr.length-1];
		
		filename = jpgname.split("\\.")[0];
		filetype = jpgname.split("\\.")[1];
		//insertImageByteaByBase64(getImageDateByUrl(url),filename,filetype);
		
		imageSaveByUrl(url,filename,filetype);
		//getImageBytea("100000200",filename,filetype);
		
	}
	public static String[] read(String url){  
        //String url="http://www.baidu.com:8080/index.jsp";  
        //String url2="ftp://baidu.com/pub/index.jsp";  
        int firSplit=url.indexOf("//");  
        String proto=url.substring(0, firSplit+2);  
        int webSplit=url.indexOf("/", firSplit+2);  
        int portIndex=url.indexOf(":",firSplit);  
        String webUrl=url.substring(firSplit+2, webSplit);  
        String port= "";  
        if(portIndex >= 0){  
            webUrl=webUrl.substring(0, webUrl.indexOf(":"));  
            port=url.substring(portIndex+1, webSplit);  
            System.out.println("端口："+port);  
        }  
        String context=url.substring(webSplit);  
        System.out.println("协议:"+proto);  
        System.out.println("网址："+webUrl);  
        System.out.println("内容："+context);
        
        return new String[]{proto,webUrl,context};
    }  
	
	public static void imageSaveByUrl(String url,String filename,String filetype){
		
		insertImageData(getImageStreamByUrl(url),filename,filetype,url);
		
	}
	
	public static InputStream getImageStreamByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(5 * 1000);  
            InputStream inStream = conn.getInputStream();//ͨ通过输入流获取图片数据
            return inStream;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 

	public static byte[] getImageDateByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestMethod("GET");  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
 	        conn.setConnectTimeout(100 * 1000);    
            InputStream inStream = conn.getInputStream();//ͨ通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
            return btImg;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
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
     * @param img
     * @param fileName
     * @author chen
     * 保存图片到本地test目录
     */

	 public static void writeImageToFile(byte[] img, String fileName){  
	        try {  
	            File file = new File("D:\\test\\" + fileName);  
	            FileOutputStream fops = new FileOutputStream(file);  
	            fops.write(img);  
	            fops.flush();  
	            fops.close();  
	            System.out.println("save ok the length is :"+img.length);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }
	 
	 /**
	  * @author chen
	  * 保存图片到数据库
	  * 
	  */
	 public static void insertImageData(InputStream in,String filename,String filetype,String url)
	    {
	        String driver = "org.postgresql.Driver";//"com.highgo.jdbc.Driver";//192.168.100.125
	        String dburl = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
	        Connection conn = null;
	        Statement stmt = null;
	        try
	        {
	            Class.forName(driver);
	            System.out.println("success find class");
	            conn = DriverManager.getConnection(dburl, "chen", "chen");
	            System.out.println("success connect");
	            stmt = conn.createStatement();
	            //driectly insert
	           // String f = new String(b);
	            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	           // outStream = b;
	           // stmt = conn.prepareStatement("select nextval('list_image_info') as list_image ");
	            ResultSet rs = stmt.executeQuery("select nextval('list_image_info')::text as list_image ") ;
	            String image_id ="";
	            if(rs.next()){   
	               // String image_id = rs.getString("list_image") ;   
	                image_id = rs.getString(1) ; // 此方法比较高效   
	            }  
	            System.out.println(" image ID is :"+ image_id);
	            rs.close();
	            
	            int in_length = in.available();
	            String sql = "INSERT INTO t_image_info_c VALUES ('"+image_id+"','"+filename+"','"+filetype+"',?,now(),"+in_length+",'"+url+"')";
	            System.out.println(sql);
	           
	            PreparedStatement ps2 = conn.prepareStatement(sql);
	            ps2.setBinaryStream(1, in,in_length);
	            ps2.executeUpdate(); 
	            ps2.close();
	           
	            System.out.println(sql);
	            stmt.close();
	            
	        }
	        catch(Exception ex)
	        {
	            ex.printStackTrace(System.out);
	        }
	        finally
	        {
	            try
	           {
	                if(stmt!=null)
	                stmt.close();
	                if(conn!=null)
	                conn.close();
	            }
	            catch(Exception ex)
	           {
	                ex.printStackTrace(System.out);
	           }
	            finally
	           {
	                System.out.println("finally");
	           }
	      }
	}
	 /**
	  * 
	  * 
	  * 
	  */
	 
	 public static void  insertImageByteaByBase64( byte[] b,String filename,String filetype,String url)
		{
			    String driver = "org.postgresql.Driver";
		        String dburl = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
		        Connection conn = null;
		        Statement stmt = null;
		        PreparedStatement ps = null;
		       
		        try
		        {
		             Class.forName(driver);
		             System.out.println("success find class");
		             conn = DriverManager.getConnection(dburl, "chen", "chen");
		             System.out.println("success connect"); 
		            
		           //  byte[] b = ByteaClass.getBytes();//得到数组byte[]
		             System.out.println("Length = " + b.length);
		             String s = Base64.encodeBytes(b, 0, b.length);
		             System.out.println("s = " + s.length());
		             stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery("select nextval('list_image_info')::text as list_image ") ;
			            String image_id ="";
			            if(rs.next()){   
			               // String image_id = rs.getString("list_image") ;   
			                image_id = rs.getString(1) ; // 此方法比较高效   
			            }  
			            System.out.println(" image ID is :"+ image_id);
			            
			            rs.close();
			            
			            String sql = "INSERT INTO t_image_info_c VALUES ('"+image_id+"','"+filename+"','"+filetype+"',?,now(),"+b.length+",'"+url+"')";
			            System.out.println(sql);
		             
		             // String sql = "insert into t_image_info(id,obj) values(?,?)";
		             String c = "decode(\'" + s + "\',\'base64\')";
		             sql = sql.replace("?",  c);
		             System.out.println("sql = " + sql);
		             ps = conn.prepareStatement(sql);
		             ps.executeUpdate();
		             System.out.println("insert");
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace(System.out);
		        }
		        finally
		        {          
		           try
		           {
		              if(ps!=null)
		              ps.close();
		              if(conn!= null)
		              conn.close();
		           }
		           catch (SQLException ex)
		           {
		               ex.printStackTrace(System.out);
		           }         
		        }
		}
	 /**
	  * 
	  * 
	  * @author chen
	  * 得到图片信息
	  * 
	  */
	 
	 public static void getImageBytea(String image_id,String filename,String filetype)
		{  
		        Connection conn = null;
		        Statement stmt = null;
		        ResultSet rs = null;
		        try
		        {
		            String driver = "org.postgresql.Driver";
			        String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
		            Class.forName(driver);
		            System.out.println("find class");
		            conn = DriverManager.getConnection(url, "chen", "chen");
	                System.out.println("connected");
		            stmt = conn.createStatement();
		            String sql = "select image_data from t_image_info_c where image_id = '"+image_id+"'";
		            rs = stmt.executeQuery(sql);
		            System.out.println("sql=" + sql);
		            if (rs.next())
		            	
		            {
		                System.out.println(rs.getMetaData().getColumnTypeName(1));
		                OutputStream ops = null;
		                InputStream ips = null;
		                File file = new File("d:" + File.separator +"test"+File.separator+filename+"."+filetype);
		                try
		                {
		                   ips = rs.getBinaryStream(1);
		                   byte[] buffer = new byte[ips.available()];//or other value like 1024
                           ops = new FileOutputStream(file);

		                   for (int i; (i = ips.read(buffer)) > 0;)
		                   {
		                        ops.write(buffer, 0, i);
		                        ops.flush();
		                   }
		                   
		                 
		                   
		                   System.out.println(" file save ok "+buffer.length);
		                }
		                catch (Exception ex)
		                {
		                    ex.printStackTrace(System.out);
		                }
		                finally
		                {
		                    ips.close();
		                    ops.close();
		                }
		            }
		            
		            
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace(System.out);
		        }
		        finally
		        {
		            try
		            {
		                if(rs!=null)
		                rs.close();
		                if(stmt!=null)
		                stmt.close();
		                if(conn!=null)
		                conn.close();
		            }
		            catch (SQLException ex)
		            {
		                 ex.printStackTrace(System.out);
		            }
		        }
		    }
		
		/**
		 * 
		 */
	 
	 public static void saveImageByteaByPath(String path)
		{  
		        Connection conn = null;
		        Statement stmt = null;
		        ResultSet rs = null;
		        try
		        {
		            String driver = "org.postgresql.Driver";
			        String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
		            Class.forName(driver);
		            System.out.println("find class");
		            conn = DriverManager.getConnection(url, "chen", "chen");
	                System.out.println("connected");
		            stmt = conn.createStatement();
		            String sql = "select image_data,image_name,image_type from t_image_info_c ";
		            rs = stmt.executeQuery(sql);
		            System.out.println("sql=" + sql);
		            while (rs.next())
		            	
		            {
		                System.out.println(rs.getMetaData().getColumnTypeName(1));
		                OutputStream ops = null;
		                InputStream ips = null;
		                String filename = "";
		                String filetype = "";
		               
		                try
		                {
		                   ips = rs.getBinaryStream(1);
		                   filename = rs.getString(2);
		                   filetype = rs.getString(3);
		                   File file = new File("d:" + File.separator +path+File.separator+filename+"."+filetype);
		                   byte[] buffer = new byte[ips.available()];//or other value like 1024
                           ops = new FileOutputStream(file);

		                   for (int i; (i = ips.read(buffer)) > 0;)
		                   {
		                        ops.write(buffer, 0, i);
		                        ops.flush();
		                   }
		                   
		                 
		                   
		                   System.out.println("d:" + File.separator +path+File.separator+filename+"."+filetype+" file save ok "+buffer.length);
		                }
		                catch (Exception ex)
		                {
		                    ex.printStackTrace(System.out);
		                }
		                finally
		                {
		                    ips.close();
		                    ops.close();
		                }
		            }
		            
		            
		        }
		        catch (Exception ex)
		        {
		            ex.printStackTrace(System.out);
		        }
		        finally
		        {
		            try
		            {
		                if(rs!=null)
		                rs.close();
		                if(stmt!=null)
		                stmt.close();
		                if(conn!=null)
		                conn.close();
		            }
		            catch (SQLException ex)
		            {
		                 ex.printStackTrace(System.out);
		            }
		        }
		    }
	 
	 public static void main2(String []args){
		 
		 saveImageByteaByPath("test");
	 }
		
}
