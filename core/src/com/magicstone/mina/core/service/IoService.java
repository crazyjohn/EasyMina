package com.magicstone.mina.core.service;

import java.io.IOException;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.handler.IoHandler;

/**
 * The io service;
 * 
 * @author Administrator
 *
 */
public interface IoService {

	/**
	 * Shutdown the io service;
	 * 
	 * @throws IOException
	 */
	public void shutdown() throws IOException;

	/**
	 * Is the service shutdown?<br>
	 * Maybe i will use the PoisonPill pattern;
	 * 
	 * @return
	 */
	public boolean isShutdown();

	/**
	 * Set the io handler;
	 * 
	 * @param handler
	 */
	public void setHandler(IoHandler handler);

	public IoFilterChain getFilterChain();

}
