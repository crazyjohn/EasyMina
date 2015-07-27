package com.magicstone.mina.example;

import java.nio.ByteBuffer;

import com.magicstone.mina.core.codec.ICodecFactory;
import com.magicstone.mina.core.codec.IDecoder;
import com.magicstone.mina.core.codec.IEncoder;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.example.msg.HelloMessage;
import com.magicstone.mina.example.msg.IMessage;
import com.magicstone.mina.example.msg.IMessageFactory;

public class CodecFactory implements ICodecFactory {
	IMessageFactory messageFactory = new IMessageFactory() {

		@Override
		public IMessage createMessage(int type) {
			switch (type) {
			case 1001:
				return new HelloMessage(type);
			}
			return null;
		}
	};

	@Override
	public IDecoder createDecoder() {
		return new LogDecoder();
	}

	@Override
	public IEncoder createEncoder() {
		return new LogEncoder();
	}

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

	class LogDecoder implements IDecoder {

		@Override
		public void decode(IoSession session, Object msg) throws Exception {
			if (msg instanceof ByteBuffer) {
				ByteBuffer readBuffer = (ByteBuffer) msg;
				int type = readBuffer.getInt(0);
				IMessage message = messageFactory.createMessage(type);
				message.read(readBuffer);
				msg = message;
				System.out.println("LogDecoder decode: " + msg);
			}
		}

	}

}
