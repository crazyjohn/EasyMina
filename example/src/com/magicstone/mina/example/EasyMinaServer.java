package com.magicstone.mina.example;

import java.io.IOException;

import com.magicstone.mina.core.codec.CodecFilter;
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
		acceptor.getFilterChain().addLast(new CodecFilter(new CodecFactory()));
		acceptor.bind("localhost", 9595);

		// sleep
		Thread.sleep(5 * 60 * 1000);
		// shutdown
		acceptor.shutdown();
	}

}
