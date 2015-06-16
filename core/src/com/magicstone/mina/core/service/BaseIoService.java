package com.magicstone.mina.core.service;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.magicstone.mina.core.filter.DefaultIoFilterChain;
import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.handler.IoHandler;
import com.magicstone.mina.core.processor.IoProcessor;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.session.NioSession;

public abstract class BaseIoService implements IoService {
	protected IoHandler handler;
	protected volatile boolean shutdown;
	/** filter chain */
	protected IoFilterChain chain = new DefaultIoFilterChain();
	/** processor */
	protected IoProcessor processor;

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

	protected void buildFilterChain() {
		this.chain = new DefaultIoFilterChain();
	}

	/**
	 * Attach the session to processor;
	 * 
	 * @param clientChannel
	 * @throws IOException
	 */
	protected IoSession createNewSession(SocketChannel clientChannel)
			throws IOException {
		IoSession session = createSession(clientChannel, handler, this.chain);
		this.processor.addSession(session);
		return session;
	}

}
