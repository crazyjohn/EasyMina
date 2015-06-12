package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.magicstone.mina.core.future.ConnectFuture;
import com.magicstone.mina.core.future.IConnectFuture;

public class NioConnector extends BaseIoService implements IConnector {
	protected SocketChannel channel;

	@Override
	public IConnectFuture connect(InetSocketAddress address) throws IOException {
		// connect to server
		channel = SocketChannel.open();
		// no-blocking
		channel.configureBlocking(false);
		IConnectFuture future = new ConnectFuture();
		boolean isConnected = channel.connect(address);
		if (isConnected) {
			// TODO: do what?
		}
		return future;
	}

}
