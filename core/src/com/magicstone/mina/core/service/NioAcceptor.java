package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;

import com.magicstone.mina.core.processor.IoProcessor;
import com.magicstone.mina.core.processor.NioProcessor;

/**
 * The nio acceptor;
 * 
 * @author crazyjohn
 *
 */
public class NioAcceptor extends BaseIoService implements IAcceptor {
	protected ServerSocketChannel serverChannel;
	protected List<IoProcessor> processors = new ArrayList<IoProcessor>();

	public NioAcceptor(int processorCount) throws IOException {
		// init
		serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		// build processors
		buildProcessors(processorCount);
	}

	/**
	 * Build processor;
	 * 
	 * @param processorCount
	 * @throws IOException
	 */
	private void buildProcessors(int processorCount) throws IOException {
		for (int i = 0; i < processorCount; i++) {
			IoProcessor processor = new NioProcessor(serverChannel);
			processors.add(processor);
			processor.start();
		}
	}

	@Override
	public void bind(String host, int port) throws IOException {
		// init and bind
		serverChannel.bind(new InetSocketAddress(host, port));
	}

}
