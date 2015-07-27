package com.magicstone.mina.example;

import com.magicstone.mina.core.codec.ICodecFactory;
import com.magicstone.mina.core.codec.IDecoder;
import com.magicstone.mina.core.codec.IEncoder;

public class LogCodecFactory implements ICodecFactory {
	

	@Override
	public IDecoder createDecoder() {
		return new LogDecoder();
	}

	@Override
	public IEncoder createEncoder() {
		return new LogEncoder();
	}

	

}
