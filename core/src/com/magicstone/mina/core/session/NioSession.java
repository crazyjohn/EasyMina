package com.magicstone.mina.core.session;

import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicLong;

import com.magicstone.mina.core.ImmutableUnit;
import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.handler.IoHandler;
import com.magicstone.mina.core.util.Constants;

/**
 * The nio session;
 * 
 * @author crazyjohn
 *
 */
public class NioSession extends BaseIoSession implements IoSession {
	protected SocketChannel channel;
	@ImmutableUnit
	protected final long id;
	/** counter */
	protected static AtomicLong counter = new AtomicLong();

	public NioSession(SocketChannel channel, IoHandler handler,
			IoFilterChain chain) {
		this.channel = channel;
		this.handler = handler;
		// set property
		this.setProperty(Constants.CHANNEL, channel);
		id = counter.incrementAndGet();
		this.chain = chain;
	}

	@Override
	public boolean isConnected() {
		return channel.isConnected();
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "NioSession: " + this.channel;
	}

}
