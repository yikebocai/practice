package org.bocai.jvm.gc;

/**
 * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class HeapOOM {

	public static void main(String[] args) {
		int SIZE=1024*1024;
		byte[][] objs=new byte[100][];
		
		for(int i=0;i<objs.length;i++){
			System.out.print("try to create obj "+i+" ...");
			objs[i]=new byte[SIZE];
			System.out.println("OK");
		}
	}

}
