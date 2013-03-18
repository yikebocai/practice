package org.bocai.decompiler.java;

import java.io.DataInputStream;
import java.io.IOException;

public class VerificationTypeInfoTool {

	public static String verify(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
		String localsOrStack = "";

		int tag = in.readUnsignedByte();

		switch (tag) {
		case 0:
			localsOrStack += "top,";
			break;
		case 1:
			localsOrStack += "int,";
			break;
		case 2:
			localsOrStack += "float,";
			break;
		case 3:
			localsOrStack += "double,";
			break;
		case 4:
			localsOrStack += "long,";
			break;
		case 5:
			localsOrStack += "null,";
			break;
		case 6:
			localsOrStack += "uninitializedThis,";
			break;
		case 7:
			int cpoolIndex = in.readUnsignedShort();
			localsOrStack += ConstantPooTool.getString(
					constantPool[cpoolIndex], constantPool) + ",";
			break;
		case 8:
			int offset = in.readUnsignedShort();
			localsOrStack += "uninitialized(" + offset + "),";
			break;
		}

		return localsOrStack;
	}

}
