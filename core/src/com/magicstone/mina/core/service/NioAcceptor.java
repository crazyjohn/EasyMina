package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.magicstone.mina.core.processor.IoProcessor;
import com.magicstone.mina.core.processor.NioProcessor;
import com.magicstone.mina.core.processor.NioProcessorPool;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.session.NioSession;

/**
 * The nio acceptor;
 * 
 * @author crazyjohn
 *
 */
public class NioAcceptor extends BaseIoService implements IAcceptor {
	private static final int DEFAULT_PROCESSOR_SIZE = Runtime.getRuntime()
			.availableProcessors() + 1;
	/** the server channel */
	protected ServerSocketChannel serverChannel;
	/** processor */
	protected IoProcessor processor;
	protected Selector selector;
	protected ExecutorService executor;
	private String acceptorThreadName = "EasyMinaAcceptor";

	public NioAcceptor(int processorCount) throws IOException {
		// init
		serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		// register
		selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, acceptorThreadName);
			}
		});
		// build processors
		buildProcessors(processorCount);
	}

	public NioAcceptor() throws IOException {
		// default size
		this(DEFAULT_PROCESSOR_SIZE);
	}

	/**
	 * Build processor;
	 * 
	 * @param processorCount
	 * @throws IOException
	 */
	private void buildProcessors(int processorCount) throws IOException {
		// buil processor, single or pool
		if (processorCount <= 1) {
			this.processor = new NioProcessor(
					Executors.newSingleThreadExecutor());
		} else {
			this.processor = new NioProcessorPool(processorCount);
		}
	}

	@Override
	public void bind(String host, int port) throws IOException {
		// init and bind
		serverChannel.bind(new InetSocketAddress(host, port));
		// startWorker
		startWorker();
	}

	private void startWorker() {
		// start accept worker
		this.executor.execute(new AcceptorWorker());
		// start processor worker
		this.processor.start();
	}

	@Override
	public void shutdown() throws IOException {
		// shutdown processor
		this.shutdown = true;
		this.processor.shutdown();
		// shutdown the executor
		this.executor.shutdownNow();
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
		this.processor.addSession(session);
	}
}
