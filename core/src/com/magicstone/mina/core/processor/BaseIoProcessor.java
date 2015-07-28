package com.magicstone.mina.core.processor;

import com.magicstone.mina.annotation.GuardedByUnit;

/**
 * The base io processor;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseIoProcessor implements IoProcessor {
	@GuardedByUnit(whoCareMe = "volatile")
	protected volatile boolean isShutDown = true;


	@Override
	public boolean isShutDown() {
		return isShutDown;
	}

	@Override
	public void startup() {
		isShutDown = false;
	}

}
