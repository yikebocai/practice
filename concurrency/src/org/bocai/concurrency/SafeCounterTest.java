package org.bocai.concurrency;

public class SafeCounterTest {
	public static void main(String[] args) {
		final SafeCounter counter = new SafeCounter();

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread("thread-" + i) {
				public void run() {
					for (int j = 0; j < 100; j++)
						counter.inc();
				}
			};
			thread.start();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("The final  counter is " + counter.get());
	}
}

class SafeCounter {
	private Integer counter = 0;

	public synchronized void inc() {
		counter++;
	}

	public Integer get() {
		return counter;
	}

}