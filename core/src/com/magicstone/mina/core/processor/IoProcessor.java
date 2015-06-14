package com.magicstone.mina.core.processor;

import java.io.IOException;

import com.magicstone.mina.core.session.IoSession;

/**
 * The io processor;
 * 
 * @author crazyjohn
 *
 */
public interface IoProcessor {
	/**
	 * Start the processor;
	 */
	public void start();

	/**
	 * Shutdown the processor;
	 */
	public void shutdown();

	/**
	 * Is the processor shutdown?
	 * 
	 * @return
	 */
	public boolean isShutDown();

	/**
	 * Add the session to processor;
	 * 
	 * @param session
	 * @throws IOException
	 */
	public void addSession(IoSession session) throws IOException;
}
