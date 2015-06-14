package com.magicstone.mina.core.filter;

import com.magicstone.mina.core.session.IoSession;

/**
 * The io filter chain;
 * 
 * @author crazyjohn
 *
 */
public interface IoFilterChain {

	public IoFilter getHeader();

	public IoFilter getFooter();

	public void fireSessionCreated(IoSession session);

	public void fireSessionOpend(IoSession session);

	public void fireMessageReceived(IoSession session, Object msg);

	public void fireMessageSend(IoSession session, Object msg);

	public void fireExceptionCaught(IoSession session, Exception e);
}
