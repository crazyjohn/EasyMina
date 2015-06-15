package com.magicstone.mina.core.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import com.magicstone.mina.core.session.IoSession;

/**
 * The nio processor pool;
 * 
 * @author crazyjohn
 *
 */
public class NioProcessorPool extends BaseIoProcessor {
	/** the io processors */
	protected List<IoProcessor> processors = new ArrayList<IoProcessor>();
	private ExecutorService executor;
	private String threadName = "EasyMinaProcessor";

	public NioProcessorPool(int processorCount) throws IOException {
		// creat executor
		executor = Executors.newCachedThreadPool(new ThreadFactory() {
			private AtomicLong counter = new AtomicLong(0);

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, threadName + "-"
						+ counter.incrementAndGet());
			}
		});
		// create processor
		for (int i = 0; i < processorCount; i++) {
			processors.add(new NioProcessor(executor));
		}
	}

	@Override
	public void addSession(IoSession session) throws IOException {
		this.getProcessor(session).addSession(session);
	}

	/**
	 * Get the processor;
	 * 
	 * @param session
	 * @return
	 */
	private IoProcessor getProcessor(IoSession session) {
		return processors.get((int) (session.getId() % processors.size()));
	}

	@Override
	public void start() {
		for (IoProcessor eachProcessor : this.processors) {
			eachProcessor.start();
		}
		super.start();
	}

	@Override
	public void shutdown() throws IOException {
		for (IoProcessor eachProcessor : this.processors) {
			eachProcessor.shutdown();
		}
		this.executor.shutdownNow();
	}

}
