package com.magicstone.mina.core.exception;

import com.magicstone.mina.core.filter.IoFilterChain;
import com.magicstone.mina.core.session.IoSession;

public interface IEasyMinaMonitor {
	public void catchException(Exception e, IoSession session,
			IoFilterChain chain);

	class DefaultMonitor implements IEasyMinaMonitor {

		@Override
		public void catchException(Exception e, IoSession session,
				IoFilterChain chain) {
			e.printStackTrace();
			chain.fireExceptionCaught(session, e);
		}

	}
}
