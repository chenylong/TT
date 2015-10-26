package com.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

import org.postgresql.util.Base64;

import sun.misc.BASE64Decoder;

public class ImageGetByteByURL {
	/**
	 * @author chen
	 * 
	 */
	 public static byte[] getImageDateByUrl(String strUrl){  
	        try {  
	            URL url = new URL(strUrl);  
	            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	            conn.setRequestMethod("GET");  
	            conn.setConnectTimeout(5 * 1000);  
	            InputStream inStream = conn.getInputStream();//ͨ通过输入流获取图片数据
	            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
	            return btImg;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	    } 
	 
	 public static void saveImageDateByUrl(String strUrl){  
	    try {  
	        URL url = new URL(strUrl);  
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        conn.setRequestMethod("GET");  
	        conn.setConnectTimeout(5 * 1000);  
	        InputStream inStream = conn.getInputStream();//ͨ通过输入流获取图片数据
	        int in_length = inStream.available();
	        System.out.println(" THE length is :"+in_length);
	        insertOid(inStream,in_length);
	        inStream.close();
	        conn.disconnect();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	
	}

 
	 
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(inStream);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		int c = bis.read();
		while ((c != -1)) {
			outStream.write(c);
			c = bis.read();
		}
		bis.close();
		return outStream.toByteArray();

		// // byte[] buffer = new byte[1024];
		// int len = 0;
		//
		// while( (len=inStream.read(buffer)) != -1 ){
		// outStream.write(buffer, 0, len);
		// }
		// inStream.close();
		// return outStream.toByteArray();
		// }
	}
	
	
	 public static void writeImageToFile(byte[] img, String fileName){  
	        try {  
	            File file = new File("D:\\test\\" + fileName);  
	            FileOutputStream fops = new FileOutputStream(file);  
	            fops.write(img);  
	            fops.flush();  
	            fops.close();  
	            System.out.println(new String(img));
	           // insertByteaByBase642(img);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	
	public static void main(String args[]){
		 String url = "http://ww2.sinaimg.cn/mw1024/a72bdd4cjw1em5n21d9y7j20p00gx752.jpg";
		  getImageBytea();
		 
		//insertByteaByBase64();
		 //saveImageDateByUrl(url);
		 
		 byte[] btImg = getImageDateByUrl(url);  
	        if(null != btImg && btImg.length > 0){  
	        	
	        	//		insertByteaByBase642(btImg);
	           // System.out.println(new String(btImg));  
	   //      writeImageToFile(btImg,"hello222.gif");
	       //  insertOid(btImg);
	        	System.out.println("ok");
	        }else{  
	            System.out.println("kong");  
	        }  
	}
	
	@SuppressWarnings("resource")
	public static void insertOid(InputStream in,int in_length)
	    {
	        String driver = "org.postgresql.Driver";//"com.highgo.jdbc.Driver";//192.168.100.125
	        String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
	        Connection conn = null;
	        Statement stmt = null;
	        try
	        {
	            Class.forName(driver);
	            System.out.println("success find class");
	            conn = DriverManager.getConnection(url, "chen", "chen");
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
	            String sql = "INSERT INTO t_image_info_c VALUES ('"+image_id+"','hello','jpg','')";
	            System.out.println(sql);
	            
	            PreparedStatement ps2 = conn.prepareStatement(sql);
	            ps2.executeUpdate(); 
	            ps2.close();
	            //or by update
	            //String f = "d://2.jpg";
	            sql = "update t_image_info_c set image_data = ?  where image_id=?";
	            System.out.println(sql);
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setBinaryStream(1, in,in_length);
	            ps.setString(2,image_id);
	            ps.executeUpdate(); 
	            
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
	
	
	public static void  insertByteaByBase64()
	{
		 String driver = "org.postgresql.Driver";//"com.highgo.jdbc.Driver";//192.168.100.125
	        String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet st = null;
	        Statement stmt = null;
	        try
	        {
	             Class.forName(driver);
	             System.out.println("success find class");
	             conn = DriverManager.getConnection(url, "chen", "chen");
	             System.out.println("success connect"); 
	             stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery("select nextval('list_image_info')::text as list_image ") ;
		            String image_id ="";
		            if(rs.next()){   
		               // String image_id = rs.getString("list_image") ;   
		                image_id = rs.getString(1) ; // 此方法比较高效   
		            }  
		            System.out.println(" image ID is :"+ image_id);
		            rs.close();
		            String sql = "INSERT INTO t_image_info_c VALUES ('"+image_id+"','hello','jpg',?)";
		            System.out.println(sql);
	             
                 ps = conn.prepareStatement(sql);
	             String fpath = "d:"+File.separator +"test"+File.separator+ "hello.jpg";
	             File file = new File(fpath);
	             InputStream ips = new FileInputStream(file);
	           
	             ps.setBinaryStream(1, ips, ips.available());
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
	                if(stmt!= null)
	                	stmt.close();
	                if(conn!= null)
	                conn.close();
	                
	           }
	           catch (SQLException ex)
	           {
	               ex.printStackTrace(System.out);
	 
	            }}
	}
	
	public static void  insertByteaByBase642( byte[] b)
	{
		    String driver = "org.postgresql.Driver";
	        String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
	        Connection conn = null;
	        Statement stmt = null;
	        PreparedStatement ps = null;
	       
	        try
	        {
	             Class.forName(driver);
	             System.out.println("success find class");
	             conn = DriverManager.getConnection(url, "chen", "chen");
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
		            String sql = "INSERT INTO t_image_info_c VALUES ('"+image_id+"','hello','jpg',?)";
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
	
	public static void getImageBytea()
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
	            String sql = "select image_data from t_image_info_c where image_id = '100000150'";
	            rs = stmt.executeQuery(sql);
	            System.out.println("sql=" + sql);
	            if (rs.next())
	            	
	            {
	                System.out.println(rs.getMetaData().getColumnTypeName(1));
	                OutputStream ops = null;
	                InputStream ips = null;
	                File file = new File("d:" + File.separator +"test"+File.separator+"binary2.jpg");
	                try
	                {
	                   ips = rs.getBinaryStream(1);
	                   byte[] buffer = new byte[ips.available()];//or other value like 1024
	                 //  ByteArrayOutputStream bos = new ByteArrayOutputStream(ips.available());  
	                   ops = new FileOutputStream(file);
	                   byte[] btImg = readInputStream(ips);
//	                   ops.write(btImg);
//	                   ops.flush();
//	                   ops.close();
//	                   ips.close();
	            
	                  
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
	
	
	@SuppressWarnings("resource")
	public static void insertBytea(byte[] b)
	    {
	        String driver = "org.postgresql.Driver";//"com.highgo.jdbc.Driver";//192.168.100.125
	        String url = "jdbc:postgresql://" + "127.0.0.1" + ":" + "5432" + "/" + "TT";
	        Connection conn = null;
	        Statement stmt = null;
	        try
	        {
	            Class.forName(driver);
	            System.out.println("success find class");
	            conn = DriverManager.getConnection(url, "chen", "chen");
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
	            String sql = "INSERT INTO t_image_info_c VALUES ('"+image_id+"','hello1','jpg','')";
	            System.out.println(sql);
	            
	            PreparedStatement ps2 = conn.prepareStatement(sql);
	            ps2.executeUpdate(); 
	            ps2.close();
	            //or by update
	            //String f = "d://2.jpg";
	            sql = "update t_image_info_c set image_data = ?  where image_id=?";
	            System.out.println(sql);
	            PreparedStatement ps = conn.prepareStatement(sql);
	            
	            ps.setString(2,image_id);
	            ps.executeUpdate(); 
	            
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
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
    
    public static void base64ToIo(String strBase64) throws IOException {
        String string = strBase64;
        String fileName = "d:" + File.separator +"test"+File.separator+"d.gif"; //生成的新文件
      //  File file = new File("d:" + File.separator +"test"+File.separator+"hello.gif");
        try {
            // 解码，然后将字节转换为文件
            byte[] bytes = new BASE64Decoder().decodeBuffer(string);   //将字符串转换为byte数组
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            FileOutputStream out = new FileOutputStream(fileName);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread); //文件写操作
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

	
}
