package com.magicstone.mina.core.future;

import com.magicstone.mina.core.session.IoSession;

/**
 * The connect future;
 * 
 * @author crazyjohn
 *
 */
public class ConnectFuture extends BaseFuture implements IConnectFuture {
	protected IoSession session;

	@Override
	public IoSession getSession() {
		return session;
	}
}
