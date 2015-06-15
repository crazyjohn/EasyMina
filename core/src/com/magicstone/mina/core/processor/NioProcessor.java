package com.magicstone.mina.core.processor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.util.Constants;

/**
 * The nio processor;
 * 
 * @author crazyjohn
 *
 */
public class NioProcessor extends BaseIoProcessor implements IoProcessor {
	/** new sessions */
	protected Queue<IoSession> newSessions = new ConcurrentLinkedQueue<IoSession>();
	/** sessions */
	protected Map<Long, IoSession> allSessions = new ConcurrentHashMap<Long, IoSession>();
	protected Selector selector;
	/** executor */
	protected ExecutorService executorService;

	public NioProcessor(ExecutorService executor) throws IOException {
		selector = Selector.open();
		// executor
		this.executorService = executor;
	}

	/**
	 * The processor's work flow;
	 * 
	 * @author crazyjohn
	 *
	 */
	class ProcessorRunnable implements Runnable {

		@Override
		public void run() {
			while (!isShutDown) {
				try {
					// 1. handle new sessions
					handleNewSessions();
					// 2. do select
					selector.select(Constants.SELECT_INTERVAL);
					Set<SelectionKey> selectedKeys = selector.selectedKeys();
					// iterator
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();
						// switch
						if (key.isReadable()) {
							handleSessionRead(key);
						} else if (key.isWritable()) {
							handleSessionWrite(key);
						}
						iterator.remove();
					}

				} catch (IOException e) {
					// TODO crazyjohn how to handle this exception?
					e.printStackTrace();
				}

			}
		}

	}

	private void handleNewSessions() {
		for (IoSession session : newSessions) {
			// fire the creat session event
			session.getFilterChain().fireSessionCreated(session);
		}
		newSessions.clear();
	}

	@Override
	public void start() {
		// start worker thread
		executorService.execute(new ProcessorRunnable());
		super.start();
	}

	@Override
	public void addSession(IoSession session) throws IOException {
		// init session
		initSession(session);
		// add to newSessions
		this.newSessions.add(session);
	}

	/**
	 * Init the session;
	 * 
	 * @param session
	 * @throws IOException
	 */
	private void initSession(IoSession session) throws IOException {
		SocketChannel channel = session.getProperty(Constants.CHANNEL);
		channel.configureBlocking(false);
		channel.register(selector, SelectionKey.OP_READ & SelectionKey.OP_WRITE);
	}

	private void handleSessionWrite(SelectionKey key) {
		// TODO crazyjohn handle write?

	}

	private void handleSessionRead(SelectionKey key) throws IOException {
		// read from channel
		SocketChannel clientChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(64);
		clientChannel.read(buffer);
		// TODO: fire this read event to filter chain
	}

	@Override
	public void shutdown() throws IOException {
		isShutDown = true;
	}

}
