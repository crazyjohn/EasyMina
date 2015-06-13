package com.magicstone.mina.core.processor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import com.magicstone.mina.core.session.IoSession;
import com.magicstone.mina.core.session.NioSession;

/**
 * The nio processor;
 * 
 * @author crazyjohn
 *
 */
public class NioProcessor extends BaseIoProcessor implements IoProcessor,
		Runnable {
	/** socket channel */
	protected ServerSocketChannel serverChannel;
	protected Selector selector;
	/** sessions */
	protected Map<Long, IoSession> allSessions = new ConcurrentHashMap<Long, IoSession>();
	protected AtomicLong counter = new AtomicLong();
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
					if (key.isAcceptable()) {
						handleNewSession(key);
					} else if (key.isReadable()) {
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

	private void handleNewSession(SelectionKey key) throws IOException {
		SocketChannel clientChannel = serverChannel.accept();
		clientChannel.register(selector, SelectionKey.OP_READ
				& SelectionKey.OP_WRITE);
		clientChannel.configureBlocking(false);
		// new session
		IoSession newSession = new NioSession(clientChannel);
		this.allSessions.put(counter.incrementAndGet(), newSession);

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
