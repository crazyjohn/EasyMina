package com.magicstone.mina.core.session;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.future.IWriteFuture;
import com.magicstone.mina.core.handler.IoHandler;
import com.magicstone.mina.core.processor.IoProcessor;
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
			.allocate(Constants.WRITE_BUFFER_SIZE);
	private Queue<ByteBuffer> encodedQueue = new ConcurrentLinkedQueue<ByteBuffer>();

	@Override
	public Queue<ByteBuffer> getWriteQueue() {
		return encodedQueue;
	}

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
		// FIXME: crazyjohn future way?
		IoProcessor processor = this.getProperty(Constants.PROCESSOR);
		if (processor != null) {
			processor.addFlushSession(this);
		}
		// flush
		fireWrite(msg);
		return null;
	}

	@Override
	public void onEncode(ByteBuffer msg) {
		this.encodedQueue.add(msg);
	}

	protected void fireWrite(Object msg) {
		this.chain.fireMessageSend(this, msg);
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
