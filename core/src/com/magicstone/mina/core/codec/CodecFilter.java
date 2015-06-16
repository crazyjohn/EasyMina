package com.magicstone.mina.core.codec;

import com.magicstone.mina.core.filter.IoFilterAdapter;
import com.magicstone.mina.core.session.IoSession;

/**
 * The codec filter;
 * 
 * @author crazyjohn
 *
 */
public class CodecFilter extends IoFilterAdapter {
	protected ICodecFactory codecFactory;
	private static final String ENCODER = "encoder";
	private static final String DECODER = "decoder";

	public CodecFilter(ICodecFactory factory) {
		this.codecFactory = factory;
	}

	@Override
	public void fireMessageReceived(IoSession session, Object msg) {
		IDecoder decoder = getDecoder(session);
		decoder.decode(session, msg);
	}

	@Override
	public void fireMessageSend(IoSession session, Object msg) {
		IEncoder encoder = getEncoder(session);
		encoder.encode(session, msg);
	}

	private IEncoder getEncoder(IoSession session) {
		IEncoder encoder = session.getProperty(ENCODER);
		if (encoder == null) {
			encoder = this.codecFactory.createEncoder();
			session.setProperty(ENCODER, encoder);
		}
		return encoder;
	}

	private IDecoder getDecoder(IoSession session) {
		IDecoder decoder = session.getProperty(DECODER);
		if (decoder == null) {
			decoder = this.codecFactory.createDecoder();
			session.setProperty(ENCODER, decoder);
		}
		return decoder;
	}
}
