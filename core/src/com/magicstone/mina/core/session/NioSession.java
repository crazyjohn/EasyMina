package com.magicstone.mina.core.session;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.magicstone.mina.core.ImmutableUnit;
import com.magicstone.mina.core.future.IWriteFuture;

/**
 * The nio session;
 * 
 * @author crazyjohn
 *
 */
public class NioSession implements IoSession {
	protected SocketChannel channel;
	@ImmutableUnit
	protected final long id;
	/** counter */
	protected static AtomicLong counter = new AtomicLong();
	/** properties */
	protected Map<String, Object> properties = new ConcurrentHashMap<String, Object>();

	public NioSession(SocketChannel channel) {
		this.channel = channel;
		id = counter.incrementAndGet();
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

	@Override
	public long getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P> P getProperty(String property) {
		Object result = this.properties.get(property);
		return (P) result;
	}

}
