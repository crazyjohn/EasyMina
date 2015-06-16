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

	/**
	 * The function object;
	 * 
	 * @author crazyjohn
	 *
	 */
	interface IFunctionObject {
		/**
		 * Do the call;
		 * 
		 * @param filter
		 */
		public void doCall(IoFilter filter);
	}

	public DefaultIoFilterChain() {
		// set head
		this.head = new IoFilterAdapter();
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
	public void fireSessionCreated(final IoSession session) {
		this.templateSkeleton(new IFunctionObject() {
			@Override
			public void doCall(IoFilter filter) {
				filter.fireSessionCreated(session);
			}
		});

	}

	/**
	 * The template method skeleton;
	 * 
	 * @param iFunction
	 */
	protected final void templateSkeleton(IFunctionObject iFunction) {
		// do head
		iFunction.doCall(head);
		// do body
		for (IoFilter eachFilter : this.body) {
			iFunction.doCall(eachFilter);
		}
		// do tail
		iFunction.doCall(tail);
	}

	@Override
	public void fireSessionOpend(final IoSession session) {
		this.templateSkeleton(new IFunctionObject() {
			@Override
			public void doCall(IoFilter filter) {
				filter.fireSessionOpend(session);
			}
		});
	}

	@Override
	public void fireMessageReceived(final IoSession session, final Object msg) {
		this.templateSkeleton(new IFunctionObject() {
			@Override
			public void doCall(IoFilter filter) {
				filter.fireMessageReceived(session, msg);
			}

		});
	}

	@Override
	public void fireMessageSend(final IoSession session, final Object msg) {
		this.templateSkeleton(new IFunctionObject() {
			@Override
			public void doCall(IoFilter filter) {
				filter.fireMessageSend(session, msg);
			}
		});
	}

	@Override
	public void fireExceptionCaught(final IoSession session, final Exception e) {
		this.templateSkeleton(new IFunctionObject() {
			@Override
			public void doCall(IoFilter filter) {
				filter.fireExceptionCaught(session, e);
			}
		});
	}

	@Override
	public void addLast(IoFilter filter) {
		body.addLast(filter);
	}

	@Override
	public void addFirst(IoFilter filter) {
		body.addFirst(filter);
	}

}
