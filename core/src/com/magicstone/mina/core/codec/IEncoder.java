package com.magicstone.mina.core.codec;

import com.magicstone.mina.core.session.IoSession;

public interface IEncoder {

	public void encode(IoSession session, Object msg) throws Exception;
}
