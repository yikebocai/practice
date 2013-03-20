public class TestString2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testPlus();
		testStringBuilder();
	}


	public static void testPlus() {
		int i = 0; 
		String str = "";
		str += i;

	}

	public static void testStringBuilder() {
		int i = 0; 
		StringBuilder sb = new StringBuilder();
	    sb.append(i);
	    
	}

}
