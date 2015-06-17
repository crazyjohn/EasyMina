package com.magicstone.mina.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.magicstone.mina.core.codec.CodecFilter;
import com.magicstone.mina.core.future.FutureListenerAdapter;
import com.magicstone.mina.core.future.IConnectFuture;
import com.magicstone.mina.core.handler.LogIoHandler;
import com.magicstone.mina.core.service.IConnector;
import com.magicstone.mina.core.service.NioConnector;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.example.msg.HelloMessage;

/**
 * The EasyMina client;
 * 
 * @author crazyjohn
 *
 */
public class EasyMinaClient {

	public static void main(String[] args) throws IOException {
		// connector
		IConnector connector = new NioConnector();
		connector.setHandler(new LogIoHandler());
		connector.getFilterChain().addLast(new CodecFilter(new CodecFactory()));
		IConnectFuture future = connector.connect(new InetSocketAddress(
				"127.0.0.1", 9595));
		// 1. addListener
		future.addListener(new FutureListenerAdapter() {
			@Override
			public void onFinished(Object result) {
				IoSession session = (IoSession) result;
				System.out.println(String.format(
						"Succeed connect to server, sessionInfo: %s", session));
				session.write(new HelloMessage("hi, EasyMina"));
			}
		});
	}
}
