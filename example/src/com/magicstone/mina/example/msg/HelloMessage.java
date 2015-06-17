package com.magicstone.mina.example.msg;

import java.nio.ByteBuffer;

import com.magicstone.mina.example.BaseMessage;

public class HelloMessage extends BaseMessage {
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	protected void readBody(ByteBuffer readBuffer) throws Exception {
		this.msg = this.readString(readBuffer);
	}

	@Override
	protected void writeBody(ByteBuffer result) throws Exception {
		this.writeString(msg, result);
	}

}
