package com.magicstone.mina.core.session;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.future.IWriteFuture;
import com.magicstone.mina.core.processor.IoHandler;

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

	/**
	 * Get the session id;
	 * 
	 * @return
	 */
	public long getId();

	/**
	 * Get the property;
	 * 
	 * @param property
	 */
	public <P> P getProperty(String property);

	// public void fireSessionCreated(IoSession session);
	//
	// public void fireSessionOpend(IoSession session);
	//
	// public void fireMessageReceived(IoSession session, Object msg);
	//
	// public void fireMessageSend(IoSession session, Object msg);
	//
	// public void fireExceptionCaught(IoSession session, Exception e);

	/**
	 * Get the io filter chain;
	 * 
	 * @return
	 */
	public IoFilterChain getFilterChain();

	/**
	 * Get the io handler;
	 * 
	 * @return
	 */
	public IoHandler getHandler();

}
