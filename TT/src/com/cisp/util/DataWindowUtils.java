package com.cisp.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi2.hssf.usermodel.HSSFCell;
import org.apache.poi2.hssf.usermodel.HSSFCellStyle;
import org.apache.poi2.hssf.usermodel.HSSFFont;
import org.apache.poi2.hssf.usermodel.HSSFRichTextString;
import org.apache.poi2.hssf.usermodel.HSSFRow;
import org.apache.poi2.hssf.usermodel.HSSFSheet;
import org.apache.poi2.hssf.usermodel.HSSFWorkbook;
import org.apache.poi2.hssf.util.HSSFColor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DataWindowUtils {

	  public static void exportCSV(JSONObject jsonDataSet, HttpServletRequest httpRequest,HttpServletResponse httpResponse)
	  {
	    String fileType = "csv";
	    String fileName = "export";

	    JSONObject jsonBody;
	    JSONObject jsonDataStores = null;
	    JSONObject jsonParameters;
	    JSONArray json_columns = null;
	    
		try {
			jsonBody = jsonDataSet.getJSONObject("body");
		    jsonDataStores = jsonBody.getJSONObject("dataStores");
		    jsonParameters = jsonBody.getJSONObject("parameters");
		    json_columns = (JSONArray)jsonParameters.get("_columns");
	
		} catch (JSONException e1) {
			//e1.printStackTrace();
		}
     
	    httpResponse.setContentType("text/csv;charset=GBK");
	    httpResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + "." + fileType);


	    if (fileType.equals("csv"))
	    {          
	        exportCsvFromClient(jsonDataStores, httpResponse, json_columns);
	    }
	  }
	  
	  /**
	   * geyj
	   * 2013-01-23
	   * 导出Excel格式
	   * @param jsonDataSet
	   * @param httpRequest
	   * @param httpResponse
	   */
	  public static void exportXLS(JSONObject jsonDataSet, HttpServletRequest httpRequest,HttpServletResponse httpResponse)
	  {
	    JSONObject jsonBody;
	    JSONObject jsonDataStores = null;
	    JSONObject jsonParameters;
	    JSONArray json_columns = null;
	    
		try {
			jsonBody = jsonDataSet.getJSONObject("body");
		    jsonDataStores = jsonBody.getJSONObject("dataStores");
		    jsonParameters = jsonBody.getJSONObject("parameters");
		    json_columns = (JSONArray)jsonParameters.get("_columns");
	
		} catch (JSONException e1) {
			//e1.printStackTrace();
		}
	    HSSFWorkbook wb = new HSSFWorkbook();
	    
		try
		{
			wb =exportXlsFromClient(jsonDataStores, httpRequest,httpResponse, json_columns);
			
			ServletOutputStream outS = httpResponse.getOutputStream();
			httpResponse.setContentType("Application/msexcel");
			httpResponse.setHeader("Content-disposition", "attachment; filename=sheet1.xls");
			wb.write(outS);
			outS.flush();
			outS.close();
		}
		catch (Exception ex)
		{
		}
	  }
	  
	  private static HSSFWorkbook exportXlsFromClient(JSONObject jsonDataStores, HttpServletRequest httpRequest,HttpServletResponse httpResponse, JSONArray json_columns) throws Exception
	  {
		
		String filePath = httpRequest.getRealPath("/resource/common/export.xls");
		FileInputStream fileIn = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(fileIn);
		
		HSSFCellStyle cellStyleTab = wb.createCellStyle();
		cellStyleTab.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyleTab.setBorderLeft((short) 1);
		cellStyleTab.setBorderRight((short) 1);
		cellStyleTab.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyleTab.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleTab.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		//设置单元格样式为自动换行
		cellStyleTab.setWrapText(true);	
		HSSFFont fontTab = wb.createFont();
		fontTab.setFontName("仿宋");
		fontTab.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTab.setFontHeightInPoints((short)14);
		cellStyleTab.setFont(fontTab);
		
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFillBackgroundColor(HSSFColor.SKY_BLUE.index);
		//设置单元格样式为自动换行
		cellStyle.setWrapText(true);	
		HSSFFont font = wb.createFont();
		font.setFontName("仿宋");
		fontTab.setFontHeightInPoints((short)12);
		cellStyle.setFont(font);
		try
		{
			HSSFSheet form = wb.getSheet("Sheet1");
			
		    List list = null;
			try {
				JSONArray jsonArrayRS = (JSONArray)jsonDataStores.getJSONObject("").getJSONObject("rowSet").get("primary");
				list = getList(jsonArrayRS.toString());
			} catch (JSONException e2) {
				//e2.printStackTrace();
			}
		    
		    List columnsList = getList(json_columns.toString());
		    int columnsCount = columnsList.size();
		    
		    String[] columns = new String[columnsCount];
		    String[] labels  = new String[columnsCount];
		    
		    HSSFRow row = form.createRow(0);
		    
		    for (int k = 0;k<columnsCount;k++)
		    {
		    	Map columnsMap = (Map)columnsList.get(k);
		    	
		    	Set set = columnsMap.keySet();
		    	
		        Iterator it=set.iterator();
		        String Key = null;
		        String strKey = null;
		        while(it.hasNext()){
		         strKey =it.next().toString();
		    	 if(!("width".equals(strKey)) && !("label".equals(strKey)) && !"dateFormat".equals(strKey) && !"format".equals(strKey) && strKey != null) Key=strKey;
		    	 if("width".equals(strKey))
		         {
		    		 form.setColumnWidth((short) (k), (short) ((Short.parseShort(columnsMap.get("width").toString()))*50));
		    	  }
		        }
		        columns[k] = Key;
		        labels[k]  = (String) columnsMap.get("label");
		        HSSFCell cell = row.createCell((short) k);
		        cell.setCellStyle(cellStyleTab);
		        cell.setCellValue(new HSSFRichTextString(labels[k]));
		    }
		    
		    for (int i = 0; i < list.size(); i++)
		    {
		        Map map = (Map)list.get(i);
		        String[] data = new String[columnsCount];
		        for (int j = 0; j < columnsCount; ++j)
		        {
		          data[j] = "";
		          data[j] = ((map.get(columns[j]) == null || (map.get(columns[j]) == "null")) ? "" : map.get(columns[j]).toString());
		          
		          HSSFRow rowData = form.createRow(i + 1);
		          HSSFCell cellData = rowData.createCell((short) j);
		          cellData.setCellStyle(cellStyle);
		          
			        if (data[j] != null && !("null".equals(data[j])))
			        {
			          if (((data[j].indexOf(44) >= 0) || (data[j].indexOf(10) >= 0)))
			          {
			        	  cellData.setCellValue(new HSSFRichTextString(data[j]));
			          }
			          else
			          {
			        	SimpleDateFormat sf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			            try{
		        		Date date = new Date(Long.parseLong(data[j]));  
						String strDate = sf.format(date).toString();
						if(strDate.substring(0, 10).equals("1970-01-01"))
						{
							cellData.setCellValue(Long.parseLong(data[j]));
						}
						else if(strDate.substring(0, 10).compareTo("1970-01-01") >0 && "2011-01-01".compareTo(strDate.substring(0, 10)) > 0)
						{
							cellData.setCellValue(new HSSFRichTextString(data[j]));
						}
						else if(strDate.substring(0, 10).compareTo("2099-12-31") > 0)
						{
							cellData.setCellValue(new HSSFRichTextString(data[j]));
						}
						 else cellData.setCellValue(new HSSFRichTextString(strDate));
			            }catch(Exception e)
			            {
			            	cellData.setCellValue(new HSSFRichTextString(data[j]));
		                }
			          }

			        }
			        
		         
		        }	        
		    }
		
			return wb;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	  }
	  
	  private static void exportCsvFromClient(JSONObject jsonDataStores, HttpServletResponse httpResponse, JSONArray json_columns)
	  {
	    PrintWriter writer = null;
		try {
			writer = httpResponse.getWriter();
		} catch (IOException e) {
			//e.printStackTrace();
		} 
	    List list = null;
		try {
			JSONArray jsonArrayRS = (JSONArray)jsonDataStores.getJSONObject("").getJSONObject("rowSet").get("primary");
			list = getList(jsonArrayRS.toString());
		} catch (JSONException e2) {
			//e2.printStackTrace();
		}
	    
	    List columnsList = getList(json_columns.toString());
	    int columnsCount = columnsList.size();
	    
	    String[] columns = new String[columnsCount];
	    String[] labels  = new String[columnsCount];
	    
	    for (int k = 0;k<columnsCount;k++)
	    {
	    	Map columnsMap = (Map)columnsList.get(k);
	    	
	    	Set set = columnsMap.keySet();
	    	
	        Iterator it=set.iterator();
	        String Key = null;
	        String strKey = null;
	        while(it.hasNext()){
	         strKey =it.next().toString();
	    	 if(!("width".equals(strKey)) && !("label".equals(strKey)) && !"dateFormat".equals(strKey) && !"format".equals(strKey) && strKey != null) Key=strKey;
	        }
	        columns[k] = Key;
	        labels[k]  = (String) columnsMap.get("label");
	    }
	    
	    CsvWriter.write(labels, writer);
	    
	    for (int i = 0; i < list.size(); i++)
	    {
	        Map map = (Map)list.get(i);
	        String[] data = new String[columnsCount];
	        for (int j = 0; j < columnsCount; ++j)
	        {
	          data[j] = "";
	          data[j] = ((map.get(columns[j]) == null || (map.get(columns[j]) == "null")) ? "" : map.get(columns[j]).toString());
	        }
	        CsvWriter.write(data, writer);
	    }
	  }
	  public static List<Map<String, Object>> getList(String jsonString) 
	  {
	      JSONArray jsonArray = null;
	      JSONObject jsonObject = null; 
	      List<Map<String, Object>> list = null; 
	      list = new ArrayList<Map<String, Object>>(); 
	      try {
			jsonArray = new JSONArray(jsonString);
		  } catch (JSONException e) {
				e.printStackTrace();
		  } 
	      for (int i = 0; i < jsonArray.length(); i++) 	    	   
	      { 	 
	        try {
				jsonObject = jsonArray.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			} 	 
	        list.add(getMap(jsonObject.toString())); 	 
	      }
	      return list;
	  }
	 
	  public static Map<String, Object> getMap(String jsonString) 	    
	  { 
	      JSONObject jsonObject;
	      try  
	      { 
	       jsonObject = new JSONObject(jsonString);
	       Iterator<String> keyIter = jsonObject.keys(); 	 
	       String key; 
	       Object value; 
	       Map<String, Object> valueMap = new HashMap<String, Object>(); 
	       while (keyIter.hasNext()) 
	       { 
	        key = (String) keyIter.next(); 
	        value = jsonObject.get(key); 
	        valueMap.put(key, value); 
	       } 
	       return valueMap; 
	      }catch (JSONException e) 
	      {  
	       e.printStackTrace(); 
	       return null;
	      }  
	       
	  }     
}
