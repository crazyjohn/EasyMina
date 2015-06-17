package com.magicstone.mina.core.codec;

import com.magicstone.mina.core.session.IoSession;

public interface IDecoder {

	public void decode(IoSession session, Object msg) throws Exception;

}
