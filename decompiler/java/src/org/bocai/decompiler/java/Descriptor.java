package org.bocai.decompiler.java;

import java.util.HashMap;
import java.util.Map;

public class Descriptor {
	private static final Map<String, String> map = new HashMap<String, String>();
	static {
		map.put("B", "int");
		map.put("C", "char");
		map.put("D", "double");
		map.put("F", "float");
		map.put("I", "int");
		map.put("J", "long");
		map.put("S", "short");
		map.put("Z", "boolean");
		map.put("V", "void");
		map.put("L", "Object");
	}
	
	public static String getMapType(String flagChar){
		return map.get(flagChar);
	}

}
