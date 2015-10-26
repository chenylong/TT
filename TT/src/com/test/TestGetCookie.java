package com.test;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

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


public class  TestGetCookie {
    private  BasicClientCookie setWeiboCookies(String name,String value,String date){  
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
    
    public TestGetCookie(){}
  
    public HttpResponse getCookieStr(){  
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
        cookieStore.addCookie(setWeiboCookies("SUS", "查看浏览器的SUS值", null)); //ok  
        cookieStore.addCookie(setWeiboCookies("SUP", "查看浏览器的SUP值", null) ); //ok  
        cookieStore.addCookie(setWeiboCookies("SUE","查看浏览器SUE值",null)); //ok  
  
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);  
        HttpGet request = new HttpGet();  
        request.setURI(URI.create("http://weibo.com"));  
        HttpResponse response = null;  
        try {  
            response = httpclient.execute(request,localContext);  
            System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            return response;
        } catch (IOException e) {  
            System.out.println(e);  
            return response;
        }  
    }
    


}
