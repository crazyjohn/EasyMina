package com.magicstone.mina.example;

import java.nio.ByteBuffer;

import com.magicstone.mina.core.codec.IDecoder;
import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.util.Constants;
import com.magicstone.mina.example.msg.HelloMessage;
import com.magicstone.mina.example.msg.IMessage;
import com.magicstone.mina.example.msg.IMessageFactory;

public class LogDecoder implements IDecoder {
	/** read buffer */
	protected ByteBuffer readBuffer = ByteBuffer
			.allocate(Constants.READ_BUFFER_SIZE);
	/** the message factory */
	private IMessageFactory messageFactory = new IMessageFactory() {

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
	public void decode(IoSession session, Object msg) throws Exception {
		if (msg instanceof ByteBuffer) {
			ByteBuffer datas = (ByteBuffer) msg;
			readBuffer.put(datas);
			readBuffer.flip();
			// header length?
			while (readBuffer.hasRemaining()) {
				if (readBuffer.limit() < Constants.MESSAGE_HEADER_LENGTH) {
					break;
				}
				int length = readBuffer.getInt(4);
				if (readBuffer.limit() < length) {
					break;
				}
				int type = readBuffer.getInt(0);
				IMessage message = messageFactory.createMessage(type);
				message.read(readBuffer);
				msg = message;
				readBuffer.compact();
				readBuffer.flip();
				System.out.println("LogDecoder decode: " + msg);
			}
			
		}
	}

}
