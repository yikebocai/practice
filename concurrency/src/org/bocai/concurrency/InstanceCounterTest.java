package org.bocai.concurrency;

import java.util.HashMap;
import java.util.Map;

public class InstanceCounterTest {

	public static void main(String[] args) {
		final Map<String, InstanceCounter> map = new HashMap<String, InstanceCounter>();

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread("thread-"+i) {
				InstanceCounter counter = new InstanceCounter();
				
				public void run() {

					for (int j = 0; j < 100; j++)
						counter.inc();
					
					map.put(Thread.currentThread().getName(), counter);
				}
			};
			thread.start();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 10; i++)
			System.out.println("counter " + i + " : " + map.get("thread-"+i).get());
	}
}

class InstanceCounter {
	private Integer counter = 0;

	public void inc() {
		counter++;
	}

	public Integer get() {
		return counter;
	}

}