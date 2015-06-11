package com.magicstone.mina.core.service;

import java.net.InetSocketAddress;

import com.magicstone.mina.core.future.IConnectFuture;

public interface IConnector {

	public IConnectFuture connect(InetSocketAddress address);
}
