package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.magicstone.mina.core.future.ConnectFuture;
import com.magicstone.mina.core.future.IConnectFuture;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.session.NioSession;

/**
 * The nio connector;
 * 
 * @author crazyjohn
 *
 */
public class NioConnector extends BaseIoService implements IConnector {
	/** the client channel */
	protected SocketChannel channel;

	@Override
	public IConnectFuture connect(final InetSocketAddress address)
			throws IOException {
		// connect to server
		channel = SocketChannel.open();
		// no-blocking
		channel.configureBlocking(false);
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
		}).start();
	}

	private void attachSessionToFuture(IoSession session, IConnectFuture future) {
		session = new NioSession(this.channel);
		future.setResult(session);
	}

}
