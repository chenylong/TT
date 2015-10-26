package com.cisp.util;

public class SMessage {
	private String users;

	private String body;
	
	private String transType;
	
	private String appSerialNo;
	
	private String succFlag;
	
	private String failReason;

	public static final String MESSAGE_TYPE_SUBTYPE = "01";
	
	public static final String MESSAGE_TYPE_SPACE =" ";
	
	public static final String MESSAGE_TYPE_APPID = "1013";
	
	public static final String MESSAGE_TYPE_PASSWORD = "neusoft";
	
	public static final String MESSAGE_TYPE = "0";
	
	public static final String MESSAGE_TYPE_ACK = "0";
	
	public static final String MESSAGE_TYPE_PRIORITY = "0";
	
	public static final String MESSAGE_TYPE_REP = "3";
	
	public static final String MESSAGE_TYPE_CHECKFLAG = "CF";
	
	public static final String MESSAGE_SUCC_FLAG = "0000";
	
	public static final String MESSAGE_CHECK_TYPE = "1001";
	
	public static final String MESSAGE_SEND_TYPE = "3011";

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getUsers()
	{
		return users;
	}

	public void setUsers(String users)
	{
		this.users = users;
	}
	
	public void setTransType(String transType)
	{
		this.transType = transType;
	}

	public String getTransType()
	{
		return transType;
	}
	
	public void setAppSerialNo(String appSerialNo)
	{
		this.appSerialNo = appSerialNo;
	}

	public String getAppSerialNo()
	{
		return appSerialNo;
	}
	
	public void setSuccFlag(String succFlag)
	{
		this.succFlag = succFlag;
	}

	public String getSuccFlag()
	{
		return succFlag;
	}
	
	public void setFailReason(String failReason)
	{
		this.failReason = failReason;
	}

	public String getFailReason()
	{
		return failReason;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		StringBuffer headBuffer = new StringBuffer();
		if("3011".equals(transType))
		{
			buffer.append(transType);
			buffer.append(MESSAGE_TYPE_SUBTYPE);
			buffer.append(MESSAGE_TYPE_APPID);
			buffer.append(returnSpace(MESSAGE_TYPE_APPID,12));
			buffer.append(appSerialNo);
			buffer.append(returnSpace(appSerialNo,35));
			buffer.append(MESSAGE_TYPE);
			buffer.append(returnSpace(MESSAGE_TYPE,3));
			buffer.append(users);
			buffer.append(returnSpace(users,255));
			buffer.append(MESSAGE_TYPE_ACK);
			buffer.append(returnSpace(30));
			buffer.append(MESSAGE_TYPE_PRIORITY);
			buffer.append(returnSpace(MESSAGE_TYPE_PRIORITY,2));
			buffer.append(MESSAGE_TYPE_REP);
			buffer.append(returnSpace(MESSAGE_TYPE_REP,2));
			buffer.append(returnSpace(3));
			buffer.append(MESSAGE_TYPE_CHECKFLAG);
			buffer.append(body);
			headBuffer.append(String.valueOf(buffer.toString().getBytes().length));
			headBuffer.append(returnSpace(String.valueOf(buffer.toString().getBytes().length),5));
			buffer = headBuffer.append(buffer);
		}
		if("1001".endsWith(transType))
		{
			buffer.append(transType);
			buffer.append(returnSpace(2));
			buffer.append(MESSAGE_TYPE_APPID);
			buffer.append(returnSpace(MESSAGE_TYPE_APPID,12));
			buffer.append(MESSAGE_TYPE_PASSWORD);
			buffer.append(returnSpace(MESSAGE_TYPE_PASSWORD,21));
			headBuffer.append(String.valueOf(buffer.length()));
			headBuffer.append(returnSpace(String.valueOf(buffer.length()),5));
			buffer = headBuffer.append(buffer);
		}
		return buffer.toString();
	}
	
	public static SMessage valueOf(String str)
	{
		SMessage message = new SMessage();
		String strContent = str.substring(5,str.length());
		String strSuccFlag = strContent.substring(0, 4);
		String strReason = null;
		if(!MESSAGE_SUCC_FLAG.equals(strSuccFlag))
		{
			strReason = strContent.substring(4, strContent.length()).trim();
		}
		message.setSuccFlag(strSuccFlag);
		message.setFailReason(strReason);
		return message;
	}
	
	public void messageInit()
	{
		this.appSerialNo = null;
		this.body = null;
		this.failReason = null;
		this.succFlag = null;
		this.transType = null;
		this.users = null;
	}
	
	private String returnSpace(String strContent,int iLength)
	{
		StringBuffer buffer = new StringBuffer();
		int iSpaceLength = iLength - strContent.length();
		for (int i=0;i<iSpaceLength;i++)
		{
			buffer.append(MESSAGE_TYPE_SPACE);
		}
		return buffer.toString();
	}
	
	private String returnSpace(int iLength)
	{
		StringBuffer buffer = new StringBuffer();
		for (int i=0;i<iLength;i++)
		{
			buffer.append(MESSAGE_TYPE_SPACE);
		}
		return buffer.toString();
	}
}
