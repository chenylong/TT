package com.cisp.util;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

public class SmsClientHandler extends IoHandlerAdapter{

	public SmsClientHandler()
	{
		super();
	}
	
	public void sessionOpened(IoSession session)
	{
		SMessage sMessage = new SMessage();
		sMessage.messageInit();
		sMessage.setAppSerialNo("00000001");
		sMessage.setBody("测试");
		sMessage.setUsers("13675873319");
		sMessage.setTransType(SMessage.MESSAGE_SEND_TYPE);
		session.write(sMessage);
		Object response = session.getAttribute("RESPONSE");
	}
	
	public void messageReceived(IoSession session,Object message)
	{
		System.out.println("接收中......");
		if(!(message instanceof ByteBuffer))
		{
			System.out.println("接收ByteBuffer......");
			return;
		}
		ByteBuffer rb = (ByteBuffer) message;
		ByteBuffer wb = ByteBuffer.allocate(rb.remaining());
		wb.put(rb);
		System.out.println(wb.toString());
		wb.flip();
		System.out.println(wb);
	}
	
	public void messageSent(IoSession session,Object message)
	{
		System.out.println(message.toString());
	}
	public void exceptionCaught(IoSession session,Throwable cause)
	{
		session.close();
	}
}
