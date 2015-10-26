package com.cisp.service;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;

import com.cisp.util.CsUtils;
import com.cisp.util.SystemUtils;
import com.ibatis.sqlmap.client.SqlMapClient;

public class CsUtilServlet extends HttpServlet{
	
	private static SqlMapClient sqlMapClient = null;
	public  void setSqlMapClient(SqlMapClient sqlMapClient) {
	    this.sqlMapClient = sqlMapClient;
    }
	static{
		try {
			Reader reader = com.ibatis.common.resources.Resources.getResourceAsReader("SqlMapConfig.xml");
		
			sqlMapClient = com.ibatis.sqlmap.client.SqlMapClientBuilder.buildSqlMapClient(reader);
			
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void init(){
		System.out.println("--------------------寮�濮嬪姞杞藉垵濮嬪寲鏁版嵁----------------------");
		System.out.println("--------------------缁撴潫鍔犺浇鍒濆鍖栨暟鎹�----------------------");
	}
	
	
}
