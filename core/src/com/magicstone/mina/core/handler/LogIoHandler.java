package com.magicstone.mina.core.handler;

import com.magicstone.mina.core.session.IoSession;

public class LogIoHandler extends IoHandlerAdapter {
	@Override
	public void onSessionCreated(IoSession session) {
		System.out.println("LogIoHandler sessionCreated: " + session);
	}

	@Override
	public void onMessageSend(IoSession session, Object msg) {
		System.out.println(String.format(
				"LogIoHandler onMessageSend: %s, msg: %s", session, msg));
	}
}
