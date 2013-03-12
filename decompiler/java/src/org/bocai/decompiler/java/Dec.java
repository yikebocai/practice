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
	private static final String magicNum = "CAFEBABE";
	private static ConstantPoolObject[] constantPool;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filename = "d:\\work\\jvm\\testfiles\\Helloworld.class";
		DataInputStream in = new DataInputStream(new FileInputStream(filename));

		if (!validFile(in))
			return;
		parseVersion(in);
		parseConstantPool(in);
		parseAccessFlag(in);
		parseThisClass(in);
		parseSuperClass(in);
		parseInterfaces(in);
		parseFields(in);
		parseMethods(in);
		parseAttributes(in);

		in.close();

	}

	// u4,magic number
	public static boolean validFile(DataInputStream in) throws IOException {
		int b = 0;
		String magic = "";
		for (int i = 0; i < 4; i++) {
			b = in.readUnsignedByte();
			magic += Integer.toHexString(b).toUpperCase();
		}

		if (magicNum.equals(magic)) {
			System.out.println("This is a JVM class file");
			return true;
		} else {
			System.err.println("This is not a JVM class file");
		}

		return false;
	}

	// u2,u2,class file version
	private static String parseVersion(DataInputStream in) throws IOException {
		// version
		int minorVersion = in.readUnsignedShort();
		int majorVersion = in.readUnsignedShort();
		String version = majorVersion + "." + minorVersion;
		System.out.println("class version is " + version);
		return version;
	}

	// u2:constant_pool_count,cp_info:constant_pool
	private static void parseConstantPool(DataInputStream in)
			throws IOException {
		// 常量池的下标是从1开始
		int constantPoolCount = in.readUnsignedShort() - 1;
		System.out.println("constant pool, " + constantPoolCount + ":");
		constantPool = new ConstantPoolObject[constantPoolCount + 1];

		for (int i = 1; i <= constantPoolCount; i++) {
			ConstantPoolObject obj = new ConstantPoolObject();
			int tag = in.readUnsignedByte();
			obj.tag = tag;
			constantPool[i] = obj;

			switch (tag) {
			case ConstantPoolTag.CONSTNAT_Utf8_info:
				int length = in.readUnsignedShort();
				byte[] bytes = new byte[length];
				in.read(bytes, 0, length);
				String value = new String(bytes, "UTF-8");
				obj.value = value;
				System.out.println("const #" + i + " = Utf8   " + value);

				break;
			case ConstantPoolTag.CONSTNAT_Integer_info:
				int intValue = in.readInt();
				obj.value = intValue;
				System.out.println("const #" + i + " = Integer   " + intValue);
				break;
			case ConstantPoolTag.CONSTNAT_Float_info:
				float floatValue = in.readFloat();
				obj.value = floatValue;
				System.out.println("const #" + i + " = Float   " + floatValue);
				break;
			case ConstantPoolTag.CONSTNAT_Long_info:
				long longValue = in.readLong();
				obj.value = longValue;
				System.out.println("const #" + i + " = Long   " + longValue);
				i++;// Long占用常量池两个位置
				break;
			case ConstantPoolTag.CONSTNAT_Double_info:
				double doubleValue = in.readDouble();
				obj.value = doubleValue;
				System.out
						.println("const #" + i + " = Double   " + doubleValue);
				i++;// Double占用常量池两个位置
				break;
			case ConstantPoolTag.CONSTNAT_Class_info:
				int nameIndex = in.readUnsignedShort();
				obj.nameIndex = nameIndex;
				System.out.println("const #" + i + " = Class   #" + nameIndex);
				break;
			case ConstantPoolTag.CONSTNAT_String_info:
				int stringIndex = in.readUnsignedShort();
				obj.stringIndex = stringIndex;
				System.out.println("const #" + i + " = String   #"
						+ stringIndex);
				break;
			case ConstantPoolTag.CONSTNAT_Fieldref_info:
			case ConstantPoolTag.CONSTNAT_Methodref_info:
			case ConstantPoolTag.CONSTNAT_InterfaceMethodref_info:
				int classIndex = in.readUnsignedShort();
				int nameAndTypeIndex = in.readUnsignedShort();
				obj.classIndex = classIndex;
				obj.nameAndTypeIndex = nameAndTypeIndex;

				String type = null;
				if (tag == ConstantPoolTag.CONSTNAT_Fieldref_info) {
					type = "Fieldref";
				} else if (tag == ConstantPoolTag.CONSTNAT_Methodref_info) {
					type = "Methodref";
				} else {
					type = "InterfaceMethodref";
				}

				System.out.println("const #" + i + " = " + type + "   #"
						+ classIndex + ",#" + nameAndTypeIndex);
				break;
			case ConstantPoolTag.CONSTNAT_NameAndType_info:
				int classIndex2 = in.readUnsignedShort();
				int descriptorIndex = in.readUnsignedShort();
				obj.classIndex = classIndex2;
				obj.descriptorIndex = descriptorIndex;
				System.out.println("const #" + i + " = NameAndType   #"
						+ classIndex2 + ",#" + descriptorIndex);
				break;
			}
		}
	}

	// u2:access flag
	private static int parseAccessFlag(DataInputStream in) throws IOException {
		int accessFlag = in.readUnsignedShort();
		System.out.println("access flag is 0x"
				+ Integer.toHexString(accessFlag) + ":");

		if ((accessFlag & AccessFlag.ACC_PUBLIC) == AccessFlag.ACC_PUBLIC) {
			System.out.println("    ACC_PUBLIC");
		}
		if ((accessFlag & AccessFlag.ACC_FINAL) == AccessFlag.ACC_FINAL) {
			System.out.println("    ACC_FINAL");
		}
		if ((accessFlag & AccessFlag.ACC_SUPER) == AccessFlag.ACC_SUPER) {
			System.out.println("    ACC_SUPER");
		}
		if ((accessFlag & AccessFlag.ACC_INTERFACE) == AccessFlag.ACC_INTERFACE) {
			System.out.println("    ACC_INTERFACE");
		}
		if ((accessFlag & AccessFlag.ACC_ABSTRACT) == AccessFlag.ACC_ABSTRACT) {
			System.out.println("    ACC_ABSTRACT");
		}
		if ((accessFlag & AccessFlag.ACC_SYNTHETIC) == AccessFlag.ACC_SYNTHETIC) {
			System.out.println("    ACC_SYNTHETIC");
		}
		if ((accessFlag & AccessFlag.ACC_ANNOTATION) == AccessFlag.ACC_ANNOTATION) {
			System.out.println("    ACC_ANNOTATION");
		}
		if ((accessFlag & AccessFlag.ACC_ENUM) == AccessFlag.ACC_ENUM) {
			System.out.println("    ACC_ENUM");
		}

		return accessFlag;
	}

	// u2:this class
	private static void parseThisClass(DataInputStream in) throws IOException {
		int thisClass = in.readUnsignedShort();
		System.out.println("\nthis class refer to #" + thisClass
				+ " of constant pool");

	}

	// u2:super class
	private static void parseSuperClass(DataInputStream in) throws IOException {
		int superClass = in.readUnsignedShort();
		System.out.println("super class refer to #" + superClass
				+ " of constant pool");
	}

	// u2:interface count,u2:interfaces
	private static void parseInterfaces(DataInputStream in) throws IOException {
		int interfacesCount = in.readUnsignedShort();
		System.out.println("\ninterfaces count is " + interfacesCount);

		for (int i = 0; i < interfacesCount; i++) {

		}
	}

	// u2:fields count,field_info:fields
	private static void parseFields(DataInputStream in) throws IOException {
		int fieldsCount = in.readUnsignedShort();
		System.out.println("\nfields count is " + fieldsCount);

		for (int i = 0; i < fieldsCount; i++) {
			int accessFlag = in.readUnsignedShort();
			System.out.print("field #" + i + " = 0x"
					+ Integer.toHexString(accessFlag) + " : ");

			if ((accessFlag & FieldAccessFlag.ACC_PUBLIC) == FieldAccessFlag.ACC_PUBLIC) {
				System.out.print("ACC_PUBLIC,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_PRIVATE) == FieldAccessFlag.ACC_PRIVATE) {
				System.out.print("ACC_PRIVATE,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_PROTECTED) == FieldAccessFlag.ACC_PROTECTED) {
				System.out.print("ACC_PROTECTED,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_STATIC) == FieldAccessFlag.ACC_STATIC) {
				System.out.print("ACC_STATIC,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_FINAL) == FieldAccessFlag.ACC_FINAL) {
				System.out.print("ACC_FINAL,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_VOLATILE) == FieldAccessFlag.ACC_VOLATILE) {
				System.out.print("ACC_VOLATILE,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_TRANSIENT) == FieldAccessFlag.ACC_TRANSIENT) {
				System.out.print("ACC_TRANSIENT,");
			}
			if ((accessFlag & FieldAccessFlag.ACC_ENUM) == FieldAccessFlag.ACC_ENUM) {
				System.out.print("ACC_ENUM,");
			}

			int nameIndex = in.readUnsignedShort();
			System.out.print("#" + nameIndex + " ");

			int descriptorIndex = in.readUnsignedShort();
			System.out.println("#" + descriptorIndex + " ");

			parseAttributes(in);

		}
	}

	// u2:methods count,method_info:methods
	private static void parseMethods(DataInputStream in) throws IOException {
		int methodsCount = in.readUnsignedShort();
		System.out.println("methods count is " + methodsCount);

		for (int i = 0; i < methodsCount; i++) {
			int accessFlag = in.readUnsignedShort();
			System.out.print("\nmethod #" + i + " = 0x"
					+ Integer.toHexString(accessFlag) + " : ");

			if ((accessFlag & MethodAccessFlag.ACC_PUBLIC) == MethodAccessFlag.ACC_PUBLIC) {
				System.out.print("ACC_PUBLIC,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_PRIVATE) == MethodAccessFlag.ACC_PRIVATE) {
				System.out.print("ACC_PRIVATE,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_PROTECTED) == MethodAccessFlag.ACC_PROTECTED) {
				System.out.print("ACC_PROTECTED,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_STATIC) == MethodAccessFlag.ACC_STATIC) {
				System.out.print("ACC_STATIC,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_FINAL) == MethodAccessFlag.ACC_FINAL) {
				System.out.print("ACC_FINAL,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_SYNCHRONIZED) == MethodAccessFlag.ACC_SYNCHRONIZED) {
				System.out.print("ACC_SYNCHRONIZED,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_BRIDGE) == MethodAccessFlag.ACC_BRIDGE) {
				System.out.print("ACC_BRIDGE,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_VARARGS) == MethodAccessFlag.ACC_VARARGS) {
				System.out.print("ACC_VARARGS,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_NATIVE) == MethodAccessFlag.ACC_NATIVE) {
				System.out.print("ACC_NATIVE,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_ABSTRACT) == MethodAccessFlag.ACC_ABSTRACT) {
				System.out.print("ACC_ABSTRACT,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_STRICT) == MethodAccessFlag.ACC_STRICT) {
				System.out.print("ACC_STRICT,");
			}
			if ((accessFlag & MethodAccessFlag.ACC_SYNTHETIC) == MethodAccessFlag.ACC_SYNTHETIC) {
				System.out.print("ACC_SYNTHETIC,");
			}

			int nameIndex = in.readUnsignedShort();
			System.out.print("#" + nameIndex + " ");

			int descriptorIndex = in.readUnsignedShort();
			System.out.println("#" + descriptorIndex + " ");

			parseAttributes(in);
		}
	}

	// u2:attributes count,attribute_info:attributes
	private static void parseAttributes(DataInputStream in) throws IOException {
		int attrCount = in.readUnsignedShort();
		System.out.println("attributes count is " + attrCount);

		for (int i = 0; i < attrCount; i++) {
			int nameIndex = in.readUnsignedShort();
			Object attrType = constantPool[nameIndex].value;
			System.out.println("attribute #" + nameIndex + " //" + attrType);

			if (AttributeType.Code.name().equals((String) attrType)) {
				parseAttributeCode(in);
			}

		}
	}

	/**
	 * <pre>
	 * u2:attribute_name_index
	 * u4:attribute_length
	 * u2:max_stack
	 * u2:max_locals
	 * u4:code_length
	 * u1:code
	 * u2:exception_table_length
	 * exception_info:exception_table
	 * u2:attributes_count
	 * attribute_info:attributes
	 * </pre>
	 * 
	 * @param in
	 * @throws IOException 
	 */
	private static void parseAttributeCode(DataInputStream in) throws IOException {
		int length=in.readInt();
		int maxStack=in.readUnsignedShort();
		int maxLocals=in.readUnsignedShort();
		int codeLength=in.readInt();
		for(int i=0;i<codeLength;i++){
			int code=in.readUnsignedByte();
			OperateCode opCode = OperateCodes.opCodes[code];
			parseOperateCode(opCode);
		}
		int exceptionTableLength=in.readUnsignedShort();
		for(int i=0;i<exceptionTableLength;i++){
			int startPc=in.readUnsignedShort();
			int endPc=in.readUnsignedShort();
			int handlePc=in.readUnsignedShort();
			int catchPc=in.readUnsignedShort();
		}
		
		parseAttributes(in);
	}

	//解析字符码指令
	private static void parseOperateCode(OperateCode opCode) {
		// TODO Auto-generated method stub
		
	}
}
