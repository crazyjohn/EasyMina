package com.magicstone.mina.core.service;

import com.magicstone.mina.core.processor.IoHandler;

public abstract class BaseIoService implements IoService {
	protected IoHandler handler;

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

}
