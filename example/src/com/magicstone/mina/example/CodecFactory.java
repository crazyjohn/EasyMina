package com.magicstone.mina.example;

import com.magicstone.mina.core.codec.ICodecFactory;
import com.magicstone.mina.core.codec.IDecoder;
import com.magicstone.mina.core.codec.IEncoder;
import com.magicstone.mina.core.session.IoSession;

public class CodecFactory implements ICodecFactory {

	@Override
	public IDecoder createDecoder() {
		return new LogDecoder();
	}

	@Override
	public IEncoder createEncoder() {
		return new LogEncoder();
	}

	static class LogEncoder implements IEncoder {

		@Override
		public void encode(IoSession session, Object msg) {
			System.out.println("LogEncoder encode: " + msg);
		}

	}

	static class LogDecoder implements IDecoder {

		@Override
		public void decode(IoSession session, Object msg) {
			System.out.println("LogDecoder decode: " + msg);
		}

	}

}
