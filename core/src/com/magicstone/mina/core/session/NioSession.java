package com.magicstone.mina.core.session;

import java.nio.channels.SocketChannel;

import com.magicstone.mina.core.future.IWriteFuture;

/**
 * The nio session;
 * 
 * @author crazyjohn
 *
 */
public class NioSession implements IoSession {
	protected SocketChannel channel;

	public NioSession(SocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public IWriteFuture write(Object msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected() {
		return channel.isConnected();
	}

}
