package com.magicstone.mina.core.service;

import com.magicstone.mina.core.processor.IoHandler;

/**
 * The io service;
 * 
 * @author Administrator
 *
 */
public interface IoService {

	/**
	 * Shutdown the io service;
	 */
	public void shutdown();

	/**
	 * Set the io handler;
	 * 
	 * @param handler
	 */
	public void setHandler(IoHandler handler);
}
