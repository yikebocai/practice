package org.bocai.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class ConstantPoolOOM {

	/**
	 * constant pool included in Method Area,so can set parms PermSize and MaxPermSize
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();

		int i = 0;
		while (true) {
			System.out.println("add string object into constant pool " + i);
			list.add(String.valueOf(i++).intern());
		}

	}

}
