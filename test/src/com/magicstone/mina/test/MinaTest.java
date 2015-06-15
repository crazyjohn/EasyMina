package com.magicstone.mina.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.magicstone.mina.core.future.FutureListenerAdapter;
import com.magicstone.mina.core.future.IConnectFuture;
import com.magicstone.mina.core.service.IAcceptor;
import com.magicstone.mina.core.service.IConnector;
import com.magicstone.mina.core.service.NioAcceptor;
import com.magicstone.mina.core.service.NioConnector;
import com.magicstone.mina.core.session.IoSession;

/**
 * The EasyMina test;
 * 
 * @author crazyjohn
 *
 */
public class MinaTest {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// acceptor
		IAcceptor acceptor = new NioAcceptor();
		acceptor.bind("localhost", 9595);

		// connector
		IConnector connector = new NioConnector();
		IConnectFuture future = connector.connect(new InetSocketAddress(
				"127.0.0.1", 9595));
		// 1. addListener
		future.addListener(new FutureListenerAdapter() {
			@Override
			public void onFinished(Object result) {
				IoSession session = (IoSession) result;
				// TODO: use this session to do something
			}
		});
		// sleep
		Thread.sleep(5 * 1000);
		// shutdown
		acceptor.shutdown();
	}

}
