package com.cisp.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class CsUtils {

	public final static String BUSINESS_MENU_ROOT = "M0000000000";
	
	public final static String SYS_MENU_ROOT      = "M0000000001";
	
	public final static String ADMIN_USER      = "admin";
	
	
	//业务角色维护-业务受理
	public final static String BUSINESS_BROLE_CODE      = "brl_hbus";
	
	public final static String PROVINCE_ORG_NO      = "33101";
	
	public final static String PROVINCE_CONVERT_ORG_NO      = "334";
	//供电单位只展现到地市层
	public final static String DISPLAY_ORG_DS      = "deplay_org_ds";
	
	//行政区域只展现到区县
	public final static String DISPLAY_STATE_QX      = "deplay_state_qx";
	
	public final static String REPORT_PROP_TYPE      = "REPORT_PROP_TYPE";
	
	public final static String REPORT_PROP_LIST      = "REPORT_PROP_LIST";
	
	public final static String REPORT_PARAMS      = "REPORT_PARAMS";
	
	public final static String REPORT_BUTTON      = "REPORT_BUTTON";
	
	public final static String REPORT_INFO      = "REPORT_INFO";
	
	public final static String REPORT_TEMPLATE      = "REPORT_TEMPLATE";
	
	public final static String REPORT_SQL_TYPE_UPDATE   = "update";
	
	public final static String REPORT_SQL_TYPE_INSERT   = "insert";
	
	public final static String Properties_CONFIG      = "proxool-config.properties";
	
	public final static String PROPERTIES_LOG_CONFIG      = "log4j.properties";
	
	//配网接单派工
	public final static String ACT_FAULT_ATTEMPER      = "14";
	
	
	//故障报修
	public final static String FAULT_BUSI_TYPE_CODE      = "001";
	
	//敏感数据分析
	public final static String TYPICALCASE_BUSI_TYPE_CODE      = "991";
	
	//故障报修流程名
	public final static String FAULT_BUSI_PROC_NAME      = "故障报修";
	
	//敏感数据分析流程名
	public final static String TYPICALCASE_DA_PROC_NAME      = "敏感数据分析";
	
	//故障受理
	public final static String NAME_FAULT_ACCEPT      = "故障受理";	
	public final static String URL_FAULT_ACCEPT      = "fault_accept";
	
	//配网接单派工
	public final static String NAME_FAULT_ATTEMPER    = "配网接单派工";	
	public final static String URL_FAULT_ATTEMPER      = "fault_attemper";
	
	//配网故障处理
	public final static String NAME_FAULT_HANDLE    = "配网故障处理";	
	public final static String URL_FAULT_HANDLE      = "fault_handle";
	
	//故障回访
	public final static String NAME_FAULT_REVISIT    = "故障回访";	
	public final static String URL_FAULT_REVISIT      = "fault_revisit";
	
	//故障归档
	public final static String NAME_FAULT_ARCHIVE    = "故障归档";	
	public final static String URL_FAULT_ARCHIVE     = "fault_archive";
	
	//流程结束
	public final static String URL_PROC_END          = "end";
	
	//故障受理角色
	public final static String ROLE_FAULT_ACCEPT      = "fault_accept_role";
	
	//配网接单派工角色
	public final static String ROLE_FAULT_ATTEMPER      = "fault_attemper_role";
	
	//配网故障处理角色
	public final static String ROLE_FAULT_HANDLE      = "fault_handle_role";
	
	//故障回访角色
	public final static String ROLE_FAULT_REVISIT      = "fault_revisit_role";
	
	//故障回访角色
	public final static String ROLE_FAULT_ARCHIVE      = "fault_archive_role";
	
	//故障报修流程定义主键名
	public final static String ID_FAULT_HANDLE_PROCESS = "faultHandleProcess";
	
	//典型案例数据分析定义主键名
	public final static String ID_TYPICALCASE_DA_PROCESS = "typicalcaseDAProcess";
	
	//流程与业务关系维护映射主键前缀
	public final static String PROC_INSTANCE = "INSTANCE";
	
	//配网接单派工环节主键前缀
	public final static String S_FAULT_ATTEMPER = "AT";
	//配网故障处理环节主键前缀
	public final static String S_FAULT_HANDLE = "HA";
	/**
	 * 验证码长度
	 */
	public final static int    AUTH_NUMBER_LEN    = 4;
	
	//消息通知类型
	public final static String  NEW_TASK_MSG_TYPE= "NEW_TASK_NOTIFY";
	
	//用户信息不存在
	public static final String CODE_USER_NOT_EXIST_ERR = "用户信息不存在";
	
	//旧密码输入错误
	public static final String CODE_OLD_PASSWORD_ERR = "旧密码输入错误，请重试";
	
	//修改成功
	public static final String CODE_CHANGE_SUCCESS = "密码修改成功";
	
	//典型案例状态--调查分析
	public static final String CASE_STATUS_INALYSIS = "调查分析";
	//典型案例状态--案例点评
	public static final String CASE_STATUS_COMMENT = "案例点评";	
	//典型案例状态--最佳点评
	public static final String CASE_STATUS_BESTCOMMENT = "最佳点评";	
	
	
	//业务受理搜索引擎可加载最大量
	public static final int BUSI_ACCEPT_LUCENE_MAX = 9999;	
	
	//数据分析库索引擎可加载最大量
	public static final int KNOWLEDGE_ANALYSIS_LUCENE_MAX = 9999;	
	
	//知识搜索显示的文字字数
	public static final int KNOWLEDGE_SHOW_WORD_LENGTH = 300;	
	
	//知识搜索显示最大段落数量
	public static final int KNOWLEDGE_MAX_NUM_FRAGMENTS = 3;	
	
	//业务受理搜索路径
	public static final String BUSI_ACCEPT_INDEX_PATH = "/resource/static/index/busiAccept/";	
	
	//数据分析库路径
	public static final String KNOWLEDGE_ANALYSIS_INDEX_PATH = "/resource/static/index/knowledgeAnalysis/";	
	
	//知识库临时存放文件路径
	public final static String PATH_KNOWLEDGE_HTML_FILES = "/resource/static/html/";
	
	//系统工程服务名
	public final static String WEB_SERVICE_NAME = "/epm";
	/**
	 * 转换str为num位数的数字（前缀为0）
	 * @param str
	 * @param num
	 * @return
	 */
	public static String convertNumToVarchar(String str,int num) {
		while (str.length() < num) {
		StringBuffer sb = new StringBuffer();
		sb.append("0").append(str);
		str = sb.toString();
		}
		return str;
	}
	
	/**
	 * 转换ISO-8859-1为GBK格式数据（解决中文前台传到后台乱码问题）
	 * geyj
	 * 2012-7-11
	 * @param str
	 * @param num
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String convertISOToGBK(String strISO) throws UnsupportedEncodingException {
		String strGBK = new String(strISO.getBytes("ISO-8859-1"), "GBK");  
		return strGBK;
	}
	
	/**
	 * 转换ISO-8859-1为UTF-8格式数据（解决中文前台传到后台乱码问题）
	 * geyj
	 * 2012-8-29
	 * @param str
	 * @param num
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String convertISOToUTF(String strISO) throws UnsupportedEncodingException {
		String strUTF = new String(strISO.getBytes("ISO-8859-1"), "UTF-8");  
		return strUTF;
	}
	

	public static JsonConfig getJsonConfig()
	{
		JsonConfig cfg = new JsonConfig();		
		cfg.registerJsonValueProcessor(Date.class,new JsonValueProcessor(){		
			public Object processObjectValue(String key, Object value,JsonConfig arg2)
			{
			     if(value==null) return "";
			     if (value instanceof Date) {
	                 return ((Date) value).getTime();
			     }
			     return value.toString();
		   }
		   public Object processArrayValue(Object value, JsonConfig arg1)
		   {
			     return null;
		   }
		});
		return cfg;
	}
	
	public static Date stringToDate(String strDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(strDate);
		return date;
	}
	
	public static Date stringToDateTime(String strDateTime) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(strDateTime);
		return date;
	}
}
