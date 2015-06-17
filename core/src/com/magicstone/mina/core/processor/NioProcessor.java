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
	private Queue<IoSession> newSessions = new ConcurrentLinkedQueue<IoSession>();
	/** sessions */
	// private Map<Long, IoSession> allSessions = new ConcurrentHashMap<Long,
	// IoSession>();
	/** needFlushSessions */
	private Map<Long, IoSession> needFlushSessions = new ConcurrentHashMap<Long, IoSession>();
	private Selector selector;
	/** executor */
	private ExecutorService executorService;

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
					int selected = selector.select(Constants.SELECT_INTERVAL);
					// 3. flush sessions
					flushSessions();
					// 4. read data from sessions
					if (selected > 0) {
						readFromSessions();
					}

				} catch (IOException e) {
					// TODO crazyjohn how to handle this exception?
					e.printStackTrace();
				}

			}
		}

	}

	/**
	 * Read data from sessions;
	 * 
	 * @throws IOException
	 */
	private void readFromSessions() throws IOException {
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		// iterator
		Iterator<SelectionKey> iterator = selectedKeys.iterator();
		while (iterator.hasNext()) {
			SelectionKey key = iterator.next();
			// switch
			if (key.isReadable()) {
				handleSessionRead(key);
			}
			iterator.remove();
		}
	}

	/**
	 * Flush the sessions;
	 * 
	 * @throws IOException
	 */
	private void flushSessions() throws IOException {
		for (IoSession session : this.needFlushSessions.values()) {
			flushSession(session);
		}
		this.needFlushSessions.clear();
	}

	/**
	 * Write the data to channel;
	 * 
	 * @param session
	 * @throws IOException
	 */
	protected void flushSession(IoSession session) throws IOException {
		Queue<ByteBuffer> writeQueue = session.getWriteQueue();
		for (ByteBuffer encodedMsg : writeQueue) {

			SocketChannel channel = session.getProperty(Constants.CHANNEL);
			if (channel == null) {
				return;
			}
			while (encodedMsg.hasRemaining()) {
				channel.write(encodedMsg);
			}
		}

	}

	/**
	 * Hanle the new sessions;
	 */
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
		channel.register(selector,
				SelectionKey.OP_READ & SelectionKey.OP_WRITE, session);
		// set processor
		session.setProperty(Constants.PROCESSOR, this);
	}

	/**
	 * Handle the session read;
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void handleSessionRead(SelectionKey key) throws IOException {
		// read from channel
		SocketChannel clientChannel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(Constants.READ_BUFFER_SIZE);
		clientChannel.read(buffer);
		IoSession session = (IoSession) key.attachment();
		// readBuffer
		buffer.flip();
		int length = buffer.limit();
		ByteBuffer readBuffer = ByteBuffer.wrap(buffer.array(), 0, length);
		readBuffer.flip();
		// fire this read event to filter chain
		session.getFilterChain().fireMessageReceived(session, readBuffer);
	}

	@Override
	public void shutdown() throws IOException {
		isShutDown = true;
		this.executorService.shutdownNow();
	}

	@Override
	public void addFlushSession(IoSession session) {
		this.needFlushSessions.put(session.getId(), session);
	}

}
