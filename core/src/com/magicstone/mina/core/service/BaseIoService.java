package com.magicstone.mina.core.service;

import com.magicstone.mina.core.processor.IoHandler;

public abstract class BaseIoService implements IoService {
	protected IoHandler handler;
	protected volatile boolean shutdown;

	@Override
	public void shutdown() {
		this.shutdown = true;
	}

	@Override
	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

}
