package org.bocai.concurrency;

public class UnsafeCounterTest {
	public static void main(String[] args) {
		final UnsafeCounter counter = new UnsafeCounter();

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread() {
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
		
		System.out.println("The final counter  is " + counter.get());
	}
}

class UnsafeCounter {
	private Integer counter = 0;

	public void inc() {
		counter++;
	}

	public Integer get() {
		return counter;
	}

}