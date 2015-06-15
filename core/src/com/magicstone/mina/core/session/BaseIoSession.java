package com.magicstone.mina.core.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.future.IWriteFuture;
import com.magicstone.mina.core.processor.IoHandler;

public abstract class BaseIoSession implements IoSession {
	/** properties */
	protected Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
	/** filter chain */
	protected IoFilterChain chain;
	protected IoHandler handler;

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

}
