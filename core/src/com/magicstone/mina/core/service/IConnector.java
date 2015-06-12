package com.magicstone.mina.core.service;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.magicstone.mina.core.future.IConnectFuture;

public interface IConnector extends IoService {

	public IConnectFuture connect(InetSocketAddress address) throws IOException;
}
