package com.magicstone.mina.core.service;

import java.io.IOException;

/**
 * The acceptor;
 * 
 * @author crazyjohn
 *
 */
public interface IAcceptor extends IoService {

	public void bind(String host, int port) throws IOException;
}
