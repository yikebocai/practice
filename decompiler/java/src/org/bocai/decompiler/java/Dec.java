package org.bocai.decompiler.java;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 对java class文件进行反编译
 * 
 * @author yikebocai@gmail.com
 * @since 2013-03-09
 * 
 */
public class Dec {
	
	private static ConstantPoolObject[] constantPool;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filename = "d:\\work\\jvm\\testfiles\\Helloworld.class";
		//String filename = "d:\\work\\jvm\\decompiler\\java\\bin\\org\\bocai\\decompiler\\java\\Dec.class";
		DataInputStream in = new DataInputStream(new FileInputStream(filename));

		if (!ClassParseTool.validFile(in))
			return;
		ClassParseTool.parseVersion(in);
		constantPool=ClassParseTool.parseConstantPool(in);
		ClassParseTool.parseAccessFlag(in);
		ClassParseTool.parseThisClass(in);
		ClassParseTool.parseSuperClass(in);
		ClassParseTool.parseInterfaces(in);
		ClassParseTool.parseFields(in,constantPool);
		ClassParseTool.parseMethods(in,constantPool);
		ClassParseTool.parseAttributes(in,constantPool);

		in.close();

	}
}
