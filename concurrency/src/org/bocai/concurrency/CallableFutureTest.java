package org.bocai.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureTest {

	public static void main(String[] args) {
		List<Future<Long>> list = new ArrayList<Future<Long>>();
		ExecutorService executor = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 500; i++) {
			Callable<Long> callable = new MyCallable();
			Future<Long> submit = executor.submit(callable);
			list.add(submit);
		}

		long sum = 0;
		System.out.println("list size : " + list.size());

		for (Future<Long> future : list) {
			try {
				sum += future.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		System.out.println("sum : " + sum);
		executor.shutdown();
	}

}
