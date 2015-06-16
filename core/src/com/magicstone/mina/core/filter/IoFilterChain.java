package com.magicstone.mina.core.filter;

import com.magicstone.mina.core.session.IoSession;

/**
 * The io filter chain;
 * 
 * @author crazyjohn
 *
 */
public interface IoFilterChain {

	/**
	 * Get the head filter;
	 * 
	 * @return
	 */
	public IoFilter getHead();

	/**
	 * Get the tail filter;
	 * 
	 * @return
	 */
	public IoFilter getTail();

	public void addLast(IoFilter filter);

	public void addFirst(IoFilter filter);

	public void fireSessionCreated(IoSession session);

	public void fireSessionOpend(IoSession session);

	public void fireMessageReceived(IoSession session, Object msg);

	public void fireMessageSend(IoSession session, Object msg);

	public void fireExceptionCaught(IoSession session, Exception e);
}
