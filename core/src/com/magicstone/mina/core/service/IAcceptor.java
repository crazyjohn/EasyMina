package com.magicstone.mina.core.service;

import java.io.IOException;

/**
 * The acceptor;
 * 
 * @author crazyjohn
 *
 */
public interface IAcceptor extends IoService {

	/**
	 * Bind the host and port for this io service;
	 * 
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public void bind(String host, int port) throws IOException;
}
