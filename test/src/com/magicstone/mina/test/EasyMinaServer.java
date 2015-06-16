package com.magicstone.mina.test;

import java.io.IOException;

import com.magicstone.mina.core.handler.LogIoHandler;
import com.magicstone.mina.core.service.IAcceptor;
import com.magicstone.mina.core.service.NioAcceptor;

/**
 * The EasyMina server;
 * 
 * @author crazyjohn
 *
 */
public class EasyMinaServer {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// acceptor
		IAcceptor acceptor = new NioAcceptor();
		acceptor.setHandler(new LogIoHandler());
		acceptor.getFilterChain();
		acceptor.bind("localhost", 9595);

		// sleep
		Thread.sleep(20 * 1000);
		// shutdown
		acceptor.shutdown();
	}

}
