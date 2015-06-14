package com.magicstone.mina.core.filter;

import java.util.LinkedList;

import com.magicstone.mina.core.NotThreadSafeUnit;
import com.magicstone.mina.core.session.IoSession;

/**
 * The default io filter chain;
 * 
 * @author crazyjohn
 *
 */
public class DefaultIoFilterChain implements IoFilterChain {
	/** head */
	protected IoFilter head;
	/** tail */
	protected IoFilter tail;
	/** body */
	@NotThreadSafeUnit
	protected LinkedList<IoFilter> body = new LinkedList<IoFilter>();

	/**
	 * The tail filter;<br>
	 * TailFilter will delegate the session to do the filter things;
	 * 
	 * @author crazyjohn
	 *
	 */
	class TailFilter extends IoFilterAdapter {

		@Override
		public void fireSessionCreated(IoSession session) {
			session.getHandler().onSessionCreated(session);
		}

		@Override
		public void fireSessionOpend(IoSession session) {
			session.getHandler().onSessionOpened(session);
		}

		@Override
		public void fireMessageReceived(IoSession session, Object msg) {
			session.getHandler().onMessageReceived(session, msg);
		}

		@Override
		public void fireMessageSend(IoSession session, Object msg) {
			session.getHandler().onMessageSend(session, msg);
		}

		@Override
		public void fireExceptionCaught(IoSession session, Exception e) {
			session.getHandler().onExceptionCaught(session, e);
		}
	}

	public DefaultIoFilterChain() {
		// set tail
		this.tail = new TailFilter();
	}

	@Override
	public IoFilter getHead() {
		return head;
	}

	@Override
	public IoFilter getTail() {
		return tail;
	}

	@Override
	public void fireSessionCreated(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireSessionOpend(IoSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireMessageReceived(IoSession session, Object msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireMessageSend(IoSession session, Object msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireExceptionCaught(IoSession session, Exception e) {
		// TODO Auto-generated method stub

	}

}
