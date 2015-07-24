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
	public void startup();

	/**
	 * Shutdown the processor;
	 * 
	 * @throws IOException
	 */
	public void shutdown() throws IOException;

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

	public void addFlushSession(IoSession session);
}
