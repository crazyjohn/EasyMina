package com.magicstone.mina.core.exception;


public interface IEasyMinaMonitor {

	public void catchException(Exception e);

	public class EasyMinaMonitor implements IEasyMinaMonitor {
		private static IEasyMinaMonitor instance = new EasyMinaMonitor();

		public static IEasyMinaMonitor getInstance() {
			return instance;
		}

		@Override
		public void catchException(Exception e) {
			e.printStackTrace();
		}

	}
}
