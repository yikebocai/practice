package org.bocai.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {
	private static final int THREAD_COUNT = 10;

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		for (int i = 0; i < 500; i++) {
			Runnable worker = new MyRunnable("thread_" + i, 10000000L + i);
			executor.execute(worker);
		}

		executor.shutdown();

		while (!executor.isTerminated()) {
		}

		System.out.println("Finished all threads");
	}

}
