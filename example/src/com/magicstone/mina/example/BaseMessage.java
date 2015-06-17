package com.magicstone.mina.example;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.magicstone.mina.example.msg.IMessage;

public abstract class BaseMessage implements IMessage {
	protected int type;

	@Override
	public void read(ByteBuffer readBuffer) throws Exception {
		readHead(readBuffer);
		readBody(readBuffer);
	}

	private void readHead(ByteBuffer readBuffer) {
		this.type = readBuffer.getInt();
	}

	@Override
	public ByteBuffer write() throws Exception {
		ByteBuffer result = ByteBuffer.allocate(128);
		writeHead(result);
		writeBody(result);
		return result;
	}

	private void writeHead(ByteBuffer result) {
		result.putInt(this.type);
	}

	@Override
	public int getType() {
		return type;
	}

	public String readString(ByteBuffer buffer)
			throws UnsupportedEncodingException {
		String result = "";
		int length = buffer.getInt();
		byte[] datas = new byte[length];
		buffer.get(datas);
		result = new String(datas, "UTF-8");
		return result;
	}
	
	public void writeString(String str, ByteBuffer buffer)
			throws UnsupportedEncodingException {
		int length = str.length();
		byte[] datas = str.getBytes("UTF-8");
		buffer.putInt(length);
		buffer.put(datas);
	}

	protected abstract void readBody(ByteBuffer readBuffer) throws Exception;

	protected abstract void writeBody(ByteBuffer result) throws Exception;
}
