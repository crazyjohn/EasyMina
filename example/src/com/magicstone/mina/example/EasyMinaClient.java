package com.magicstone.mina.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.magicstone.mina.core.future.FutureListenerAdapter;
import com.magicstone.mina.core.future.IConnectFuture;
import com.magicstone.mina.core.service.IConnector;
import com.magicstone.mina.core.service.NioConnector;
import com.magicstone.mina.core.session.IoSession;

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
		IConnectFuture future = connector.connect(new InetSocketAddress(
				"127.0.0.1", 9595));
		// 1. addListener
		future.addListener(new FutureListenerAdapter() {
			@Override
			public void onFinished(Object result) {
				IoSession session = (IoSession) result;
				System.out.println(session);
			}
		});
	}
}