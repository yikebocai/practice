package org.bocai.concurrency;

public class MyRunnable implements Runnable {
	private String threadName;
	private final long countUtil;

	MyRunnable(String threadName, long countUtil) {
		this.threadName = threadName;
		this.countUtil = countUtil;
	}

	@Override
	public void run() {
		long sum = 0;
		for (long i = 1; i < countUtil; i++) {
			sum += i;
		}
		System.out.println(threadName + " : " + sum);
	}

}
