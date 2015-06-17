package com.magicstone.mina.example.msg;

import java.nio.ByteBuffer;

public interface IMessage {

	public void read(ByteBuffer readBuffer) throws Exception;

	public ByteBuffer write() throws Exception;

	public int getType();
}
