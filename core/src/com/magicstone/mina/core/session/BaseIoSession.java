package com.magicstone.mina.core.session;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.future.IWriteFuture;
import com.magicstone.mina.core.handler.IoHandler;
import com.magicstone.mina.core.util.Constants;

/**
 * The base io session;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseIoSession implements IoSession {
	/** properties */
	protected Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
	/** filter chain */
	protected IoFilterChain chain;
	/** io handler */
	protected IoHandler handler;
	private ByteBuffer writeBuffer = ByteBuffer
			.allocate(Constants.WRITE_BUFFER_SIZE);;

	@Override
	public IoHandler getHandler() {
		return handler;
	}

	@Override
	public IoFilterChain getFilterChain() {
		return chain;
	}

	@Override
	public IWriteFuture write(Object msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P> P getProperty(String name) {
		Object result = this.properties.get(name);
		return (P) result;
	}

	@Override
	public void setProperty(String name, Object value) {
		this.properties.put(name, value);
	}

	@Override
	public ByteBuffer getWriteBuffer() {
		return writeBuffer;
	}

}
