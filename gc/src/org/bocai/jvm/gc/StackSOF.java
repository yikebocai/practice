package org.bocai.jvm.gc;

/**
 * VM Args:-Xss128k
 * 
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class StackSOF {

	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	/**
	 * stackLength is about 2403,average 54 bytes per stack frame
	 */
	public static void main(String[] args) {
		StackSOF sof = new StackSOF();
		try {
			sof.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length:" + sof.stackLength);
			e.printStackTrace();
		}
	}

}
