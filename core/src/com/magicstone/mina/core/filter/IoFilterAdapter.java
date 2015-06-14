package com.magicstone.mina.core.filter;

import com.magicstone.mina.core.session.IoSession;

/**
 * The io filter adapter;
 * 
 * @author crazyjohn
 *
 */
public class IoFilterAdapter implements IoFilter {

	@Override
	public void fireSessionCreated(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireSessionOpend(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireMessageReceived(IoSession session, Object msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireMessageSend(IoSession session, Object msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireExceptionCaught(IoSession session, Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public IoFilter getNextFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fireSessionClosed(IoSession session) {
		// TODO Auto-generated method stub
		
	}

}
