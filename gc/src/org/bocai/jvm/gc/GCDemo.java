package org.bocai.jvm.gc;

/**
 * VM Params:-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * @author yikebocai@gmail.com
 * @since 2013-3-20
 * 
 */
public class GCDemo {

	private static final int _1MB=1024*1024;
	
	public static void main(String[] args) {
		//testMinorGC();
		testFullGC();

	}

	/**
	 * Minor GC Demo
	 */
	@SuppressWarnings("unused")
	private static void testMinorGC() {
		byte[] alloc1=new byte[2*_1MB];
		byte[] alloc2=new byte[2*_1MB];
		byte[] alloc3=new byte[1*_1MB];
		
		//first Minor GC,5M Eden->Old
		byte[] alloc4=new byte[4*_1MB];
		
		byte[] alloc5=new byte[_1MB/4];
		
		//second Minor GC,alloc5:Eden->Surivior
		byte[] alloc6=new byte[4*_1MB];
		
		//if MaxTenuringThreshold>2 ,then move alloc5 into old,but whatever alloc5 is moved into old,why? 
		alloc4=null;
		//byte[] alloc7=new byte[4*_1MB];
		//System.out.println(alloc5.length);
	}
	
	
	/**
	 * Full GC Demo
	 */
	@SuppressWarnings("unused")
	private static void testFullGC() {
		byte[] alloc1=new byte[2*_1MB];
		byte[] alloc2=new byte[2*_1MB];
		byte[] alloc3=new byte[1*_1MB];
		
		//first Minor GC
		byte[] alloc4=new byte[4*_1MB];
		byte[] alloc5=new byte[3*_1MB];
		
		//second Minor GC
		byte[] alloc6=new byte[4*_1MB];
		
		alloc1=null;
		alloc2=null;
		//first Full GC
		byte[] alloc7=new byte[2*_1MB];
	}
	

}
