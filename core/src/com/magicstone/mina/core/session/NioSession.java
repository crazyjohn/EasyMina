package com.magicstone.mina.core.session;

import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicLong;

import com.magicstone.mina.core.ImmutableUnit;
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

	public NioSession(SocketChannel channel) {
		this.channel = channel;
		// set property
		this.setProperty(Constants.CHANNEL, channel);
		id = counter.incrementAndGet();
	}

	@Override
	public boolean isConnected() {
		return channel.isConnected();
	}

	@Override
	public long getId() {
		return id;
	}

}
