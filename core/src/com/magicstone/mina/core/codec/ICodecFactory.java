package com.magicstone.mina.core.codec;

/**
 * The codec factory;
 * 
 * @author crazyjohn
 *
 */
public interface ICodecFactory {

	/**
	 * Create the decoder;
	 * 
	 * @return
	 */
	public IDecoder createDecoder();

	/**
	 * Create the encoder;
	 * 
	 * @return
	 */
	public IEncoder createEncoder();
}
