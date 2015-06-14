package com.magicstone.mina.core.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.future.IWriteFuture;

public abstract class BaseIoSession implements IoSession {
	/** properties */
	protected Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
	/** filter chain */
	protected IoFilterChain chain;

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
	public <P> P getProperty(String property) {
		Object result = this.properties.get(property);
		return (P) result;
	}

}
