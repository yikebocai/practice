package org.bocai.jvm.gc;

/**
 * VM Args:-Xss2M
 * 
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class StackOOM {

	/**
	 * VM Stack size is about equals  RAM size reduce Heap size
	 * @param args
	 */
	public static void main(String[] args) {
		int counter=0;
		
		while (true) {
			System.out.println("create thread "+(counter++)+" ...");
			Thread thread = new Thread(new Runnable() {
				public void run() {
					while (true){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) { 
							e.printStackTrace();
						}
					}
				}
			});
			
			thread.start();
		}

	}

}
