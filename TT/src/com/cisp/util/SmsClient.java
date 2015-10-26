package com.cisp.util;

import java.net.InetSocketAddress;

import org.apache.mina.filter.LoggingFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;

public class SmsClient {

	private static final String HOSTNAME = "10.137.253.2";
	
	private static final int PORT = 10000;
	
	private static final int CONNECT_TIMEOUT = 5000;//秒
	
	public static void main(String[] args) throws Throwable
	{
		SocketConnector connector = new SocketConnector();
		SocketConnectorConfig cfg = new SocketConnectorConfig();
		cfg.setConnectTimeout(CONNECT_TIMEOUT);
		cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		cfg.getFilterChain().addLast("logger", new LoggingFilter());
		connector.connect(new InetSocketAddress(HOSTNAME, PORT),new SmsClientHandler(),cfg);		
	}
}
