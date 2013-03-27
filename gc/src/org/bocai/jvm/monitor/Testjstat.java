package org.bocai.jvm.monitor;

/**
 * VM Args:-Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * 
 * @author yikebocai@gmail.com
 * @since 2013-3-26
 * 
 */
public class Testjstat {
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) throws InterruptedException {
		while (true) {
			byte[] alloc1 = new byte[2 * _1MB];
			byte[] alloc2 = new byte[2 * _1MB];
			byte[] alloc3 = new byte[1 * _1MB];

			// first Minor GC
			byte[] alloc4 = new byte[4 * _1MB];
			byte[] alloc5 = new byte[3 * _1MB];

			// second Minor GC
			byte[] alloc6 = new byte[4 * _1MB];

			alloc1 = null;
			alloc2 = null;
			// first Full GC
			byte[] alloc7 = new byte[2 * _1MB];

			Thread.sleep(100);
		}

	}

}
