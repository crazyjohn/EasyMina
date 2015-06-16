package com.magicstone.mina.core.service;

import java.nio.channels.SocketChannel;

import com.magicstone.mina.core.filter.DefaultIoFilterChain;
import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.handler.IoHandler;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.session.NioSession;

public abstract class BaseIoService implements IoService {
	protected IoHandler handler;
	protected volatile boolean shutdown;
	/** filter chain */
	protected IoFilterChain chain = new DefaultIoFilterChain();

	@Override
	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

	@Override
	public IoFilterChain getFilterChain() {
		return chain;
	}

	protected IoSession createSession(SocketChannel channel, IoHandler handler,
			IoFilterChain chain) {
		IoSession session = new NioSession(channel, handler, chain);
		return session;
	}

}
