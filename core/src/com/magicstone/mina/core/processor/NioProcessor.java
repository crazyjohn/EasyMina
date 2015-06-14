package com.magicstone.mina.core.processor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.magicstone.mina.core.session.IoSession;

/**
 * The nio processor;
 * 
 * @author crazyjohn
 *
 */
public class NioProcessor extends BaseIoProcessor implements IoProcessor,
		Runnable {
	private static final String CHANNEL = "channel";
	/** socket channel */
	protected ServerSocketChannel serverChannel;
	protected Selector selector;
	/** new sessions */
	protected Queue<IoSession> newSessions = new ConcurrentLinkedQueue<IoSession>();
	/** sessions */
	protected Map<Long, IoSession> allSessions = new ConcurrentHashMap<Long, IoSession>();
	protected ExecutorService executor;

	public NioProcessor(ServerSocketChannel serverChannel) throws IOException {
		this.serverChannel = serverChannel;
		selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		// executor
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void run() {
		while (!isShutDown) {
			try {
				selector.select();
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

	@Override
	public void start() {
		// start worker thread
		executor.execute(this);
		super.start();
	}

	@Override
	public void addSession(IoSession session) throws IOException {
		// init session
		initSession(session);
		// add to newSessions
		this.newSessions.add(session);
		// FIXME: crazyjoh fire the creat session event
	}

	/**
	 * Init the session;
	 * 
	 * @param session
	 * @throws IOException
	 */
	private void initSession(IoSession session) throws IOException {
		SocketChannel channel = session.getProperty(CHANNEL);
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

}
