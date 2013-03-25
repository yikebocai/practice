package org.bocai.jvm.monitor;


/**
 * @author yikebocai@gmail.com
 * @since 2013-3-25
 * 
 */
public class Testjps {

	public static void main(String[] args) throws InterruptedException {
		
		if (args != null && args.length == 1) {
			System.out.println("Hello," + args[0] + "!");
		}

		while (true) {
			Thread.sleep(1000);
		}
	}

}
