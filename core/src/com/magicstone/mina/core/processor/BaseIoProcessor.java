package com.magicstone.mina.core.processor;

import com.magicstone.mina.core.GuardedByUnit;

public abstract class BaseIoProcessor implements IoProcessor {
	@GuardedByUnit(whoCareMe = "volatile")
	protected volatile boolean isShutDown = true;

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isShutDown() {
		return isShutDown;
	}

	@Override
	public void start() {
		isShutDown = false;
	}

}
