package com.magicstone.mina.core.future;

/**
 * The io future;
 * 
 * @author crazyjohn
 *
 */
public interface IoFuture {
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

	/**
	 * Await the result unInterruptbly;
	 * 
	 * @return
	 */
	public boolean awaitUnInterruptble();

	/**
	 * Timed await;
	 * 
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public boolean await(long timeout) throws InterruptedException;

	/**
	 * Is the operation done;
	 * 
	 * @return
	 */
	public boolean isDone();

	/**
	 * Set the result;
	 * 
	 * @param result
	 */
	public void setResult(Object result);

	/**
	 * Get the result;
	 * 
	 * @return
	 * @throws Exception
	 */
	public Object getResult() throws Exception;
}
