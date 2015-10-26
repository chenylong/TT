package com.cisp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.util.Streams;

import org.htmlparser.beans.StringBean;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
import org.textmining.text.extraction.WordExtractor;

public class FileTool {

	/**
	 *  
	 * 2012-8-30
	 * 浠庢湇鍔＄涓嬭浇鏂囨。鍒板鎴风
	 * @param fileName 鏂囦欢鍚�,濡倀est
	 * @param path    缁濆璺緞锛屽F:/test.raq
	 * @param fileType 鏂囦欢绫诲瀷锛屽raq,txt
	 * @param response
	 * @throws IOException
	 */
	public static void download(String fileName,String path,String fileType,HttpServletResponse response) throws IOException
	{
        BufferedInputStream bis = null;
        BufferedOutputStream bos =  null;
        OutputStream fos = null;
        InputStream  fis = null;
        String filePath = path;
        File uploadFile = new File(filePath);
        fis = new FileInputStream(uploadFile);
        bis = new BufferedInputStream(fis);
        fos = response.getOutputStream();
        bos = new BufferedOutputStream(fos);
        
        response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName+"."+fileType,"UTF-8"));
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while((bytesRead = bis.read(buffer,0,8192))!= -1)
        {
        	bos.write(buffer, 0, bytesRead);
        }
        bos.flush();
        fis.close();
        bis.close();
        fos.close();
        bos.close();
	}
	
	/**
	 *  
	 * 2012-8-30
	 * 瀹㈡埛绔笂浼犳枃浠跺埌鏈嶅姟鍣�
	 * @param inFile 涓婁紶鏂囦欢锛屾牸寮忎负FormFile
	 * @param path  涓婁紶鍒版湇鍔″櫒鐨勭粷瀵硅矾寰勶紝濡侳:\test.raq
	 * @throws IOException 
	 */
	public static void upload(File inFile,String path) throws IOException
	{
		BufferedInputStream  in  = new BufferedInputStream(new FileInputStream(inFile));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path)));
		Streams.copy(in,out,true);
	}
	
	/**
	 *  
	 * 2012-8-30
	 * 灏嗘枃浠舵彃鍏ュ埌瀵瑰簲琛ㄧ殑Blob瀛楁
	 * @param inFile 涓婁紶鏂囦欢锛屾牸寮忎负File
	 * @param tableName 琛ㄥ悕
	 * @param columnName 琛˙lob瀛楁瀵瑰簲鐨勫瓧娈靛悕绉�
	 * @param rowId 鏇存柊琛ㄦ寚瀹氳鐨勫敮涓�鏍囩ず
	 * @param rowName 鏇存柊琛ㄦ寚瀹氳鐨勫敮涓�鏍囩ず瀛楁鍚嶇О
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws IOException 
	 */

	/**
	 *  
	 * 2013-04-10
	 * 浠庢暟鎹簱璇诲彇鏂囦欢Blob瀛楁
	 * @param tableName 琛ㄥ悕
	 * @param columnName 琛˙lob瀛楁瀵瑰簲鐨勫瓧娈靛悕绉�
	 * @param rowId 鏇存柊琛ㄦ寚瀹氳鐨勫敮涓�鏍囩ず
	 * @param rowName 鏇存柊琛ㄦ寚瀹氳鐨勫敮涓�鏍囩ず瀛楁鍚嶇О
	 * @param FileName涓哄鍑哄悗鐨勬枃浠跺悕
	 * @param FileType涓哄鍑哄悗鏂囦欢鐨勫悗缂�,濡俤oc
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws IOException 
	 */

	/**
	 *  
	 * 2012-8-30
	 * 琛˙lob鏁版嵁杞寲涓篎ile鏂囦欢
	 * @param path 缁濆璺緞 濡侳:\test.raq
	 * @param tableName 琛ㄦ槑
	 * @param columnName 琛˙lob瀵瑰簲鐨勫瓧娈靛悕
	 * @param rowId   琛ㄦ煇涓�琛屽搴旂殑鍞竴鏍囪瘑鐨勫��
	 * @param rowName 琛ㄥ敮涓�鏍囪瘑瀵瑰簲鐨勮〃瀛楁鍚嶇О
	 * @return
	 * @throws Exception
	 */

	public static String getPDFTextContent(String file)
	{
		String text = "";	
		String pdfFile = file ;	
		PDDocument document = null;
		try{
			//瑁呰浇鏂囦欢
			document = PDDocument.load(pdfFile);
			//鐢≒DFTextStripper鏉ユ彁鍙栨枃鏈�
			PDFTextStripper stripper = new PDFTextStripper();
			text = stripper.getText(document);
		}catch(Exception e)
		{
			text = "";
		}
		finally{
			try{
				if(document != null)
				{
					//鍏抽棴PDF Document
					document.close();
				}
			}catch(Exception ex)
			{
				
			}
		}
		return text;
	}
	
	public static String getExcelTextContent(String file)
	{
		StringBuffer sb = new StringBuffer("");
		try{
		  //鎵撳紑鏂囦欢
		  Workbook book = Workbook.getWorkbook(new File(file));
		  //sheet鏁伴噺
		  int numSheet = book.getNumberOfSheets();
		  //閬嶅巻shhet
		  for(int i= 0;i<numSheet;i++)
		  {
			  //鑾峰彇褰撳墠宸ヤ綔鍗曞璞�
			  Sheet sheet = book.getSheet(i);
			  //琛屾暟
			  int numRow = sheet.getRows();
			  //鍒楁暟
			  int numCol = sheet.getColumns();
			  for(int j=0;j<numRow;j++)
			  {
				  for(int k=0;k<numCol;k++)
				  {
					  Cell c= sheet.getCell(k,j);
					  sb.append(c.getContents());
				  }
			  }
		  }
		}catch(Exception e)
		{
			sb.append("");
		}
		return sb.toString();
	}
	
	public static String getWordTextContent(String file)
	{
		String text = "";
		try{
			//鍒涘缓杈撳叆娴佽鍙朌OC鏂囦欢
			FileInputStream in = new FileInputStream(new File(file));
			//鍒涘缓WordExtractor
			WordExtractor extractor = new WordExtractor();
			//鎻愬彇鏂囨湰
			text = extractor.extractText(in);
		}catch(Exception e)
		{
			text = "";
		}
		return text;
	}
	
	public static String getHTMLTextContent(String file) throws UnsupportedEncodingException
	{
		StringBean bean = new StringBean();
		bean.setLinks(false);
		bean.setReplaceNonBreakingSpaces(true);
		bean.setCollapse(true);
		bean.setURL(file);
		String text = bean.getStrings();
		return CsUtils.convertISOToGBK(text);
	}
	
	public static String getTXTTextCOntent(String file)
	{
		StringBuffer buffer = new StringBuffer(file);
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while(s!= null)
			{
				buffer.append(s);
				s= br.readLine();
			}
			br.close();
		}catch(Exception e)
		{
			buffer.append("");
		}
		return buffer.toString();
	}
	
	/**
	 *  
	 * 2013-06-17
	 * 鑾峰彇PDF銆丒xcel銆乄ord銆丠tml銆乻html銆乼xt鏂囦欢鐨勬枃鏈俊鎭�
	 * @param file
	 * @return
	 */
	public static String getFileTextContent(String file)
	{
		String text = "";
		try{
			//鏍规嵁鎵╁睍鍚嶅垽鏂枃浠剁被鍨�
			String ext = file.substring(file.lastIndexOf(".")+1);
			if(ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("html") || ext.equalsIgnoreCase("shtml"))
			{
				//澶勭悊HTML鏂囦欢
				text = getHTMLTextContent(file);
			}else if(ext.equalsIgnoreCase("doc"))
			{
				//澶勭悊Word鏂囦欢
				text = getWordTextContent(file);
			}else if(ext.equalsIgnoreCase("xls"))
			{
				//澶勭悊Excel鏂囦欢
				text = getExcelTextContent(file);
			}else if(ext.equalsIgnoreCase("pdf"))
			{
				//澶勭悊PDF鏂囦欢
				text = getPDFTextContent(file);
			}else{
				text = getTXTTextCOntent(file);
			}
		}catch(Exception e)
		{
			text = "";
		}
		return text;
				
	}
	
	/**
	 *  
	 * 2013-10-14
	 * 涓婁紶html鏂囦欢鍒版湇鍔″櫒
	 * @param fileContent 涓婁紶鏂囦欢锛屾牸寮忎负String
	 * @param path  涓婁紶鍒版湇鍔″櫒鐨勭粷瀵硅矾寰勶紝濡侳:\test.raq
	 * @throws IOException 
	 */
	public static void upload(String fileContent,String path) throws IOException
	{
	    File file = new File(path);
	    if (file.exists()) {
		    file.delete();
		}
		byte[] b = fileContent.getBytes();
	
		OutputStream writer = new FileOutputStream(file);
		writer.write(b);
		writer.close();
	}
}
