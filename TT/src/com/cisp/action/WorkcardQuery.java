package com.cisp.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import com.cisp.service.WorkcardQueryService;
import com.cisp.util.CsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.test.ImageGetByteByURL;
import com.test.TestGetCookie;


public class WorkcardQuery extends ActionSupport {
	
	private WorkcardQueryService workcardQueryService;
	

	public WorkcardQueryService getWorkcardQueryService() {
		return workcardQueryService;
	}

	public void setWorkcardQueryService(WorkcardQueryService workcardQueryService) {
		this.workcardQueryService = workcardQueryService;
	}
	

	/**
	 * geyj
	 * 2012-10-12
	 * 综合查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void queryWorkcard() throws JSONException, SQLException, IOException 
	{
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request  =ServletActionContext.getRequest();
		
		String typeId   = request.getParameter("typeId");
   		
		System.out.println(typeId);
		String data = "";
		HashMap hm = new HashMap();
		List<?>  workcardList =  null;		
		workcardList = workcardQueryService.getiWorkcardQuery().queryWorkcard(hm);		
		JSONArray jsonArray = JSONArray.fromObject(workcardList,CsUtils.getJsonConfig());
		data =  jsonArray.toString();

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data);

	}
	
	public void queryNumCountById() throws JSONException, SQLException, IOException 
	{
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request  =ServletActionContext.getRequest();
		
		String typeId   = request.getParameter("typeId");
   		
		System.out.println(typeId);
		String data = "";
		HashMap hm = new HashMap();
		hm.put("NUMID", typeId);
		List<?>  workcardList =  null;		
		workcardList = workcardQueryService.getiWorkcardQuery().queryNumCountById(hm);		
		JSONArray jsonArray = JSONArray.fromObject(workcardList,CsUtils.getJsonConfig());
		data =  jsonArray.toString();

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data);

	}
	
	public void queryDomeSql() throws JSONException, SQLException, IOException 
	{
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request  =ServletActionContext.getRequest();
		
		String sql   = request.getParameter("json");
   		
				
		System.out.println(sql);
		String data = "";
		HashMap hm = new HashMap();
		hm.put("NUMID", "");
		//String sql ="select '01' label,'02' value";
		hm.put("SQL", sql);
		List<?>  workcardList =  null;		
		workcardList = workcardQueryService.getiWorkcardQuery().queryDomeSql(hm);		
		JSONArray jsonArray = JSONArray.fromObject(workcardList,CsUtils.getJsonConfig());
		data =  jsonArray.toString();

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(data);

	}
	
	public void getCookie() throws IOException{
		
		HttpServletResponse response2=ServletActionContext.getResponse();
		HttpServletRequest request3  =ServletActionContext.getRequest();
		
		  DefaultHttpClient httpclient = new DefaultHttpClient();  
	        httpclient.getParams().setParameter("http.protocol.cookie-policy",  
	                CookiePolicy.BROWSER_COMPATIBILITY);  
	        HttpParams params = httpclient.getParams();  
	        HttpConnectionParams.setConnectionTimeout(params, 5000);  
	        HttpConnectionParams.setSoTimeout(params, 1000*60*10);  
	        DefaultHttpRequestRetryHandler dhr = new DefaultHttpRequestRetryHandler(3,true);  
	        HttpContext localContext = new BasicHttpContext();  
	        HttpRequest request2 = (HttpRequest) localContext.getAttribute(  
	                ExecutionContext.HTTP_REQUEST);  
	        httpclient.setHttpRequestRetryHandler(dhr);  
	        BasicCookieStore cookieStore = new BasicCookieStore();  
	  
	        /** 
	         *  weibo.com 
	         */  
	        cookieStore.addCookie(setWeiboCookies("SUS", "�鿴�������SUSֵ", null)); //ok  
	        cookieStore.addCookie(setWeiboCookies("SUP", "�鿴�������SUPֵ", null) ); //ok  
	        cookieStore.addCookie(setWeiboCookies("SUE","�鿴�����SUEֵ",null)); //ok  
	  
	        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);  
	        HttpGet request = new HttpGet();  
	        request.setURI(URI.create("http://weibo.com"));  
	        HttpResponse response = null;  
	        try {  
	            response = httpclient.execute(request,localContext);  
	            System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
	        	response2.getWriter().print(EntityUtils.toString(response.getEntity(), "utf-8"));
	            
	        } catch (IOException e) {  
	            System.out.println(e);  
	            response2.getWriter().print(e.toString());
	            
	        }  
		
		
		
		
		
		
		
		
	}
	/**
	 * 
	 * @author chen1023 E-mail:chen1023@foxmail.com
	 * @version 创建时间：2015年10月24日 上午11:55:56
	 * 类说明
	 * @param name
	 * @param value
	 * @param date
	 * @return
	 */
	public  BasicClientCookie setWeiboCookies(String name,String value,String date){  
        BasicClientCookie2 cookie = new BasicClientCookie2(name,value);  
        cookie.setDomain(".weibo.com");  
        cookie.setPath("/");  
        if (StringUtils.isNotBlank(date)) {  
            cookie.setExpiryDate(new Date(date));  
        }else{  
            cookie.setExpiryDate(null);  
        }  
        return cookie;  
    }  
	
/**
 * 
 * @author chen1023 E-mail:chen1023@foxmail.com
 * @version 创建时间：2015年10月24日 上午11:57:48
 * 类说明
 * @throws SQLException 
 * @throws IOException 
 */
	public void viewPicAll() throws SQLException, IOException {
		
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request  =ServletActionContext.getRequest();
		
		String PageId   = request.getParameter("pageid");
		
	    int limitInt = 10;
	    
	    if (null != PageId && isNumeric(PageId)&& Integer.parseInt(PageId)>10){
	    	
	    	limitInt =Integer.parseInt(PageId);
	    }
	    
		String data = "{\"rows\":["+
	"{\"imageid\":\"FI-SW-01\",\"imagename\":\"imagetype\",\"imagedate\":10.00},"+
	"{\"imageid\":\"K9-DL-01\",\"imagename\":\"Dalmation\",\"imagedate\":12.00},"+
	"{\"imageid\":\"RP-SN-01\",\"imagename\":\"Rattlesnake\",\"imagedate\":12.00},"+
	"{\"imageid\":\"RP-SN-01\",\"imagename\":\"Rattlesnake\",\"imagedate\":12.00},"+
	"{\"imageid\":\"RP-LI-02\",\"imagename\":\"Iguana\",\"imagedate\":12.00}"+
    "]}";
		HashMap hm = new HashMap();
		hm.put("LIMITINT", limitInt);
		List<?>  workcardList =  null;		
		workcardList = workcardQueryService.getiWorkcardQuery().viewPicbyId(hm);

   
	
		JSONArray jsonArray = JSONArray.fromObject(workcardList,CsUtils.getJsonConfig());
		data =  jsonArray.toString();
		System.out.println(data);
        response.setCharacterEncoding("UTF-8");
   		response.getWriter().print(data);

		
		
		
	}
	
	/**
	 * 
	 * @author chen1023 E-mail:chen1023@foxmail.com
	 * @version 创建时间：2015年10月24日 下午12:34:03
	 * 类说明
	 * @throws SQLException
	 * @throws IOException
	 */
	public void viewPicbyId() throws SQLException, IOException {
		
		HttpServletResponse response=ServletActionContext.getResponse();
		HttpServletRequest request  =ServletActionContext.getRequest();
		
		String pid   = request.getParameter("pid");
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
          String sql = "select image_data from t_image_info_c where image_id ='"+pid+"'";
          rs = stmt.executeQuery(sql);
          System.out.println("sql=" + sql);
          if (rs.next())
          	
          {
              System.out.println(rs.getMetaData().getColumnTypeName(1));
         //     OutputStream ops = null;
              InputStream ips = null;
          //    File file = new File("d:" + File.separator +"test"+File.separator+"binary2.jpg");
              try
              {
                 ips = rs.getBinaryStream(1);
                 
                 response.setContentType("image/jpg");
                 OutputStream outs = response.getOutputStream();
                 byte[] btImg = ImageGetByteByURL.readInputStream(ips);
                 outs.write(btImg);
                outs.flush();
             //   response.setCharacterEncoding("UTF-8");
         	//	response.getWriter().print(ips);
               
              }
              catch (Exception ex)
              {
                  ex.printStackTrace(System.out);
              }
              finally
              {
                  ips.close();
       //           ops.close();
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
	
	public  boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	}
	
}