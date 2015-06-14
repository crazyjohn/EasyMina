package com.magicstone.mina.core.filter;

import com.magicstone.mina.core.session.IoSession;

/**
 * The io filter;
 * 
 * @author crazyjohn
 *
 */
public interface IoFilter {

	/**
	 * Fire the session created;
	 * 
	 * @param session
	 */
	public void fireSessionCreated(IoSession session);

	/**
	 * Fire the session opened;
	 * 
	 * @param session
	 */
	public void fireSessionOpend(IoSession session);

	/**
	 * Fire the message received;
	 * 
	 * @param session
	 */
	public void fireMessageReceived(IoSession session, Object msg);

	/**
	 * Fire the message send;
	 * 
	 * @param session
	 */
	public void fireMessageSend(IoSession session, Object msg);

	/**
	 * Fire the exception caught;
	 * 
	 * @param session
	 */
	public void fireExceptionCaught(IoSession session, Exception e);

	/**
	 * Get next io filter;
	 * 
	 * @return
	 */
	public IoFilter getNextFilter();
}
