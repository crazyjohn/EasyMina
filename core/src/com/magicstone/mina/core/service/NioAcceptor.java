package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.magicstone.mina.core.processor.IoProcessor;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.session.NioSession;

/**
 * The nio acceptor;
 * 
 * @author crazyjohn
 *
 */
public class NioAcceptor extends BaseIoService implements IAcceptor {
	protected ServerSocketChannel serverChannel;
	protected IoProcessor processors;
	protected Selector selector;
	protected Executor executor;

	public NioAcceptor(int processorCount) throws IOException {
		// init
		serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		// register
		selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		executor = Executors.newSingleThreadExecutor();
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
		// TODO: crazyjohn build processor
	}

	@Override
	public void bind(String host, int port) throws IOException {
		// init and bind
		serverChannel.bind(new InetSocketAddress(host, port));
		// startWorker
		startWorker();
	}

	private void startWorker() {
		this.executor.execute(new AcceptorWorker());
	}

	/**
	 * The accept worker;
	 * 
	 * @author crazyjohn
	 *
	 */
	class AcceptorWorker implements Runnable {

		@Override
		public void run() {
			while (!shutdown) {
				// 1. select
				int selected = 0;
				try {
					selected = selector.select();
				} catch (IOException e) {
					// TODO crazyjohn handle this exception
					e.printStackTrace();
				}
				// 2. any new commer?
				if (selected > 0) {
					try {
						handleNewCommers();
					} catch (IOException e) {
						// TODO crazyjohn handle this exception
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * Handle new commer;
	 * 
	 * @throws IOException
	 */
	private void handleNewCommers() throws IOException {
		Set<SelectionKey> keys = selector.selectedKeys();
		Iterator<SelectionKey> iterator = keys.iterator();
		while (iterator.hasNext()) {
			SelectionKey key = iterator.next();
			if (key.isAcceptable()) {
				SocketChannel clientChannel = this.serverChannel.accept();
				attachToProcessor(clientChannel);
			}
			iterator.remove();
		}
	}

	/**
	 * Attach the session to processor;
	 * 
	 * @param clientChannel
	 * @throws IOException
	 */
	private void attachToProcessor(SocketChannel clientChannel)
			throws IOException {
		IoSession session = new NioSession(clientChannel);
		this.processors.addSession(session);
	}
}
