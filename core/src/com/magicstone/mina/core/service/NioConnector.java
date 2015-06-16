package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.magicstone.mina.core.future.ConnectFuture;
import com.magicstone.mina.core.future.IConnectFuture;
import com.magicstone.mina.core.processor.IoProcessor;
import com.magicstone.mina.core.processor.NioProcessor;
import com.magicstone.mina.core.session.IoSession;

/**
 * The nio connector;
 * 
 * @author crazyjohn
 *
 */
public class NioConnector extends BaseIoService implements IConnector {
	/** the client channel */
	private SocketChannel channel;
	private static String threadName = "EasyMinaConnector";

	public NioConnector(IoProcessor processor) {
		this.processor = processor;
	}

	public NioConnector() throws IOException {
		this(new NioProcessor(
				Executors.newSingleThreadExecutor(new ThreadFactory() {

					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, threadName);
					}
				})));
		// start processor
		this.processor.start();
	}

	@Override
	public IConnectFuture connect(final InetSocketAddress address)
			throws IOException {
		// connect to server
		channel = SocketChannel.open();
		// no-blocking
		channel.configureBlocking(false);
		// build filterChain
		buildFilterChain();
		final IConnectFuture future = new ConnectFuture();
		boolean isConnected = channel.connect(address);
		// build session when connect succeed
		final IoSession session = null;
		if (isConnected) {
			// do attach
			attachSessionToFuture(session, future);
		} else {
			// start connect worker
			startConnectWorker(address, session, future);
		}
		return future;
	}

	/**
	 * Start the connect worker;
	 * 
	 * @param address
	 * @param session
	 * @param future
	 */
	private void startConnectWorker(final InetSocketAddress address,
			final IoSession session, final IConnectFuture future) {
		// do connect
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (!channel.finishConnect()) {
						channel.connect(address);
					}
					// set result
					attachSessionToFuture(session, future);
				} catch (IOException e) {
					// set exception to result
					future.setResult(e);
				}
			}
		}, "NioConnector").start();
	}

	private void attachSessionToFuture(IoSession session, IConnectFuture future)
			throws IOException {
		session = createNewSession(this.channel);
		future.setResult(session);
	}

	@Override
	public void shutdown() throws IOException {
		this.shutdown = true;
	}

}
