package com.magicstone.mina.core.session;

import com.magicstone.mina.core.future.IWriteFuture;

/**
 * The io session;
 * 
 * @author crazyjohn
 *
 */
public interface IoSession {
	/**
	 * Write the msg;
	 * 
	 * @param msg
	 * @return
	 */
	public IWriteFuture write(Object msg);

	/**
	 * Get whether the session is connected;
	 * 
	 * @return
	 */
	public boolean isConnected();
}
