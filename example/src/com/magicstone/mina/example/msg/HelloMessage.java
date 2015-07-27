package com.magicstone.mina.example.msg;

import java.nio.ByteBuffer;

import com.magicstone.mina.example.BaseMessage;

public class HelloMessage extends BaseMessage {
	private String msg;

	public HelloMessage(String msg) {
		super(1001);
		this.msg = msg;

	}

	public HelloMessage(int type) {
		this("");
	}

	public String getMessage() {
		return msg;
	}

	@Override
	protected void readBody(ByteBuffer readBuffer) throws Exception {
		this.msg = this.readString(readBuffer);
	}

	@Override
	protected void writeBody(ByteBuffer result) throws Exception {
		this.writeString(msg, result);
	}

	@Override
	public String toString() {
		return String
				.format("%s msg: %s", this.getClass().getSimpleName(), msg);
	}

}
