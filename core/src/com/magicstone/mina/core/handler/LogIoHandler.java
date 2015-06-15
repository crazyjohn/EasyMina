package com.magicstone.mina.core.handler;

import com.magicstone.mina.core.session.IoSession;

public class LogIoHandler extends IoHandlerAdapter {
	@Override
	public void onSessionCreated(IoSession session) {
		System.out.println("LogIoHandler sessionCreated: " + session);
	}
}
