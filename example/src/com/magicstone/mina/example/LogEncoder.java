package com.magicstone.mina.example;

import com.magicstone.mina.core.codec.IEncoder;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.example.msg.IMessage;

class LogEncoder implements IEncoder {

	@Override
	public void encode(IoSession session, Object msg) throws Exception {
		System.out.println("LogEncoder encode: " + msg);
		if (msg instanceof IMessage) {
			IMessage message = (IMessage) msg;
			session.onEncode(message.write());
		}
	}

}
