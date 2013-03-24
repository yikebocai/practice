package org.bocai.jvm.gc;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class DirectMemoryOOM {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field unsafeField=Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe=(Unsafe)unsafeField.get(null);
		
		int counter=0;
		while(true){
			System.out.println("allocate direct memory "+(counter++));
			unsafe.allocateMemory(10*1024*1024);
		}
	}

}
