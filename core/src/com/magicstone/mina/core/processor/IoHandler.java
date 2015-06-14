package com.magicstone.mina.core.processor;

import com.magicstone.mina.core.session.IoSession;

/**
 * The io handler;
 * 
 * @author crazyjohn
 *
 */
public interface IoHandler {

	public void onSessionCreated(IoSession session);

	public void onSessionOpened(IoSession session);

	public void onMessageReceived(IoSession session, Object msg);

	public void onMessageSend(IoSession session, Object msg);

	public void onSessionClosed(IoSession session);

	public void onExceptionCaught(IoSession session, Exception e);

}
