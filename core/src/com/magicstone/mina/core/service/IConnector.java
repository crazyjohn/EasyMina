package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.magicstone.mina.core.future.IConnectFuture;

public interface IConnector extends IoService {

	/**
	 * Connect to the future;
	 * 
	 * @param address
	 * @return the connect future;
	 * @throws IOException
	 */
	public IConnectFuture connect(InetSocketAddress address) throws IOException;
}
