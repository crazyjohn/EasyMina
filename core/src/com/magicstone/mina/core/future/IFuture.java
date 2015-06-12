package com.magicstone.mina.core.future;

public interface IFuture {
	/**
	 * Add future listener;
	 * 
	 * @param futureListener
	 */
	public void addListener(IFutureListener futureListener);

	/**
	 * Remove the future listener;
	 * 
	 * @param futureListener
	 */
	public void removeListener(IFutureListener futureListener);

	public boolean awaitUnInterruptble();

	public boolean await(long timeout) throws InterruptedException;

	public boolean isDone();
}
