package com.magicstone.mina.core.service;

import java.nio.channels.ServerSocketChannel;

public class NioAcceptor extends BaseIoService implements IAcceptor {
	protected ServerSocketChannel server;

	@Override
	public void bind(String host, int port) {
		// TODO Auto-generated method stub

	}

}
