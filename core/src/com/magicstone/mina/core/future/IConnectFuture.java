package com.magicstone.mina.core.future;

import com.magicstone.mina.core.session.IoSession;

public interface IConnectFuture extends IFuture {
	public IoSession getSession();
}
