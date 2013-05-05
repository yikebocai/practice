package org.bocai.concurrency;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Long> {

	@Override
	public Long call() throws Exception {
		long sum = 0;

		for (int i = 0; i < 100; i++) {
			sum += i;
		}
		return sum;
	}

}
