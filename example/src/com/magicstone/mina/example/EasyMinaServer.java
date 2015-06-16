package com.magicstone.mina.example;

import java.io.IOException;

import com.magicstone.mina.core.codec.CodecFilter;
import com.magicstone.mina.core.codec.ICodecFactory;
import com.magicstone.mina.core.codec.IDecoder;
import com.magicstone.mina.core.codec.IEncoder;
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
		acceptor.getFilterChain().addLast(new CodecFilter(new ICodecFactory() {

			@Override
			public IEncoder createEncoder() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IDecoder createDecoder() {
				// TODO Auto-generated method stub
				return null;
			}
		}));
		;
		acceptor.bind("localhost", 9595);

		// sleep
		Thread.sleep(20 * 1000);
		// shutdown
		acceptor.shutdown();
	}

}
