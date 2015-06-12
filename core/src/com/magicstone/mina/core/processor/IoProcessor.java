package com.magicstone.mina.core.processor;

public interface IoProcessor {
	public void start();

	public void shutdown();

	public boolean isShutDown();
}
