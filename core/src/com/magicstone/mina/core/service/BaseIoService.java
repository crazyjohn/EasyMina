package com.magicstone.mina.core.service;

import com.magicstone.mina.core.handler.IoHandler;

public abstract class BaseIoService implements IoService {
	protected IoHandler handler;
	protected volatile boolean shutdown;


	@Override
	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

}
