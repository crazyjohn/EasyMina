package com.magicstone.mina.core.session;

import java.nio.ByteBuffer;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.future.IWriteFuture;
import com.magicstone.mina.core.handler.IoHandler;

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
	 * @param name
	 */
	public <P> P getProperty(String name);

	public void setProperty(String name, Object value);

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

	/**
	 * Get the write buffer;
	 * 
	 * @return
	 */
	public ByteBuffer getWriteBuffer();

	/**
	 * Flush;
	 */
	public void flush();
}
