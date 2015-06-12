package com.magicstone.mina.core.future;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.magicstone.mina.core.GuardedByUnit;
import com.magicstone.mina.core.ThreadSafeUnit;

/**
 * The base future;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public abstract class BaseFuture implements IFuture {
	/** listeners */
	protected List<IFutureListener> listeners = new CopyOnWriteArrayList<IFutureListener>();
	/** the flag state */
	@GuardedByUnit(whoCareMe = "volatile")
	protected volatile boolean isDone;
	protected Lock lock = new ReentrantLock();
	/** maybe i can use the readWriteLock */
	protected Condition done = lock.newCondition();
	@GuardedByUnit(whoCareMe = "done")
	protected Object result;

	/**
	 * Get the result;
	 * 
	 * @return
	 */
	public Object getResult() {
		lock.lock();
		try {
			return result;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Set the result;
	 * 
	 * @param result
	 */
	public void setResult(Object result) {
		lock.lock();
		try {
			this.result = result;
			// set flag
			this.isDone = true;
			this.done.signalAll();
		} finally {
			lock.unlock();
		}

	}

	@Override
	public void addListener(IFutureListener futureListener) {
		this.listeners.add(futureListener);
	}

	@Override
	public boolean awaitUnInterruptble() {
		lock.lock();
		try {
			while (!isDone) {
				done.awaitUninterruptibly();
			}
		} finally {
			lock.unlock();
		}
		// notify, right?
		notifyListeners();
		return true;
	}

	@Override
	public boolean await(long timeout) throws InterruptedException {
		lock.lock();
		try {
			while (!isDone) {
				done.wait(timeout);
			}
		} finally {
			lock.unlock();
		}
		// notify, right?
		notifyListeners();
		return true;
	}

	@Override
	public void removeListener(IFutureListener futureListener) {
		this.listeners.remove(futureListener);
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

	private void notifyListeners() {
		for (IFutureListener listener : this.listeners) {
			listener.onFinished(result);
		}
	}

}
