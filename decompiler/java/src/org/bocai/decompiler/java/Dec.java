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
			System.out.println("attribute #" + nameIndex);
			System.out.println("  " + attrType + ":");

			int length = in.readInt();
			if (AttributeType.Code.name().equals((String) attrType)) {
				parseAttributeCode(in);
			} else if (AttributeType.Exceptions.name()
					.equals((String) attrType)) {
				parseAttributeException(in);
			} else if (AttributeType.LineNumberTable.name().equals(
					(String) attrType)) {
				parseAttributeLineNumberTable(in);
			} else if (AttributeType.LocalVariableTable.name().equals(
					(String) attrType)) {
				parseAttributeLocalVariableTable(in);
			} else if (AttributeType.SourceFile.name()
					.equals((String) attrType)) {
				parseAttributeSouceFile(in);
			} else if (AttributeType.ConstantValue.name().equals(
					(String) attrType)) {
				parseAttributeConstantValue(in);
			} else if (AttributeType.InnerClass.name()
					.equals((String) attrType)) {
				parseAttributeInnerClass(in);
			} else if (AttributeType.Deprecated.name()
					.equals((String) attrType)) {
				parseAttributeDeprecated(in);
			} else if (AttributeType.Synthetic.name().equals((String) attrType)) {
				parseAttributeSynthetic(in);
			} else if (AttributeType.StackMapTable.name().equals(
					(String) attrType)) {
				parseAttributeStackMapTable(in);
			} else {
				System.err.println("Error attribute!");
			}

		}
	}

	// u2:number_of_entries,stack_map_frame: entries[number_of_entries];
	private static void parseAttributeStackMapTable(DataInputStream in)
			throws IOException {
		int number = in.readUnsignedShort();
		System.out.println("\tnumber_of_entries = " + number);

		for (int i = 0; i < number; i++) {
			int frameType = in.readUnsignedByte();
			String frameTypeTag=null;
			
			if (frameType < 64) {
				frameTypeTag="SAME";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
			} else if (frameType >= 64 && frameType < 128) {
				frameTypeTag="SAME_LOCALS_1_STACK_ITEM";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
			} else if (frameType >= 128 && frameType < 247) {
				frameTypeTag="SAME_LOCALS_1_STACK_ITEM_EXTENDED";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
			}
			else if (frameType ==247) {
				frameTypeTag="CHOP";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
			}
			else if (frameType >= 248 && frameType < 251) {
				frameTypeTag="SAME_FRAME_EXTENDED";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
			}
			else if (frameType >= 252 && frameType < 254) {
				frameTypeTag="APPEND";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
			}
			else if (frameType ==255) {
				frameTypeTag="FULL_FRAME";
				System.out.println("\tframe_type="+frameType+"  /*"+frameTypeTag+"*/");
				
				int offsetDelta=in.readUnsignedShort();
				System.out.println("\t\toffset_delta = "+offsetDelta);
				
				String locals="[";
				int numberOfLocals=in.readUnsignedShort();
				for(int j=0;j<numberOfLocals;j++){
					int tag=in.readUnsignedByte();
					 
					switch(tag){
					case 0:
						locals+="top,";
						break;
					case 1:
						locals+="int,";
						break;
					case 2:
						locals+="float,";
						break;
					case 3:
						locals+="double,";
						break;
					case 4:
						locals+="long,";
						break;
					case 5:
						locals+="null,";
						break;
					case 6:
						locals+="uninitializedThis,";
						break;
					case 7:
						int cpoolIndex=in.readUnsignedShort();
						locals+=ConstantPooTool.getString(constantPool[cpoolIndex], constantPool)+",";
						break;
					case 8:
						int offset=in.readUnsignedShort();
						locals+="uninitialized("+offset+"),";
						break;
					}
					
				}
				locals+="]";
				System.out.println("\t\tlocals = "+locals);
				
				String stack="[";
				int numberOfStack=in.readUnsignedShort();
				for(int j=0;j<numberOfStack;j++){
					int tag=in.readUnsignedByte();
					 
					switch(tag){
					case 0:
						stack+="top,";
						break;
					case 1:
						stack+="int,";
						break;
					case 2:
						stack+="float,";
						break;
					case 3:
						stack+="double,";
						break;
					case 4:
						stack+="long,";
						break;
					case 5:
						stack+="null,";
						break;
					case 6:
						stack+="uninitializedThis,";
						break;
					case 7:
						int cpoolIndex=in.readUnsignedShort();
						stack+=ConstantPooTool.getString(constantPool[cpoolIndex], constantPool)+",";
						break;
					case 8:
						int offset=in.readUnsignedShort();
						stack+="uninitialized("+offset+"),";
						break;
					}
					
				}
				stack+="]";
				System.out.println("\t\tstack = "+stack);
				
			}
			 
			
		}

	}

	private static void parseAttributeSynthetic(DataInputStream in) {
		System.out.println("Synthetic:true");

	}

	private static void parseAttributeDeprecated(DataInputStream in) {
		System.out.println("Deprecated:true");

	}

	private static void parseAttributeInnerClass(DataInputStream in)
			throws IOException {
		int number = in.readUnsignedShort();

		for (int i = 0; i < number; i++) {
			int innerClassInfoIndex = in.readUnsignedShort();
			int outerClassInfoIndex = in.readUnsignedShort();
			int innerNameIndex = in.readUnsignedShort();
			int innerClassAccessFlag = in.readUnsignedShort();

			int index1 = constantPool[innerClassInfoIndex].classIndex;
			String clazz1 = (String) constantPool[index1].value;
			System.out.println("\t#" + index1 + " //" + clazz1);

			int index2 = constantPool[outerClassInfoIndex].classIndex;
			String clazz2 = (String) constantPool[index2].value;
			System.out.println("\t#" + index1 + " //" + clazz2);

			System.out.println("\t#" + innerNameIndex + " //"
					+ constantPool[innerNameIndex].value);

			System.out.println("innerClassAccessFlag: 0x"
					+ Integer.toHexString(innerClassAccessFlag) + ":");

			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_PUBLIC) == InnerClassAccessFlag.ACC_PUBLIC) {
				System.out.println("\tACC_PUBLIC");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_PRIVATE) == InnerClassAccessFlag.ACC_PRIVATE) {
				System.out.println("\tACC_PRIVATE");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_PROTECTED) == InnerClassAccessFlag.ACC_PROTECTED) {
				System.out.println("\tACC_PROTECTED");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_STATIC) == InnerClassAccessFlag.ACC_STATIC) {
				System.out.println("\tACC_STATIC");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_FINAL) == InnerClassAccessFlag.ACC_FINAL) {
				System.out.println("\tACC_FINAL");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_INTERFACE) == InnerClassAccessFlag.ACC_INTERFACE) {
				System.out.println("\tACC_INTERFACE");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_ABSTRACT) == InnerClassAccessFlag.ACC_ABSTRACT) {
				System.out.println("\tACC_ABSTRACT");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_SYNTHETIC) == InnerClassAccessFlag.ACC_SYNTHETIC) {
				System.out.println("\tACC_SYNTHETIC");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_ANNOTATION) == InnerClassAccessFlag.ACC_ANNOTATION) {
				System.out.println("\tACC_ANNOTATION");
			}
			if ((innerClassAccessFlag & InnerClassAccessFlag.ACC_ENUM) == InnerClassAccessFlag.ACC_ENUM) {
				System.out.println("\tACC_ENUM");
			}
		}
	}

	private static void parseAttributeConstantValue(DataInputStream in)
			throws IOException {
		int index = in.readUnsignedShort();
		System.out.println("\t#" + index + "  //" + constantPool[index].tag
				+ " " + constantPool[index].value);

	}

	private static void parseAttributeSouceFile(DataInputStream in)
			throws IOException {
		int index = in.readUnsignedShort();
		System.out.println("\t#" + index + "  //" + constantPool[index].value);
	}

	// u2:local_variable_table_length,local_variable_info:local_variable_table
	private static void parseAttributeLocalVariableTable(DataInputStream in)
			throws IOException {
		int tableLength = in.readUnsignedShort();
		for (int i = 0; i < tableLength; i++) {
			// 局部变量生命周期开始的字节码偏移量和覆盖长度，结合起来表示局部变更的作用域范围
			int startpc = in.readUnsignedShort();
			int length = in.readUnsignedShort();
			// 指向常量池CONSTANT_utf8_info型常量的索引，分别代表局部变量的名称和描述符
			int nameIndex = in.readUnsignedShort();
			int descriptorIndex = in.readUnsignedShort();
			// 局部变量在栈桢局部变量表中Slot的位置,当时变量数据类型为64位类型(double和long),它占用的Slot为index和index+1两个位置
			int index = in.readUnsignedShort();

			System.out.println("\tLV: " + startpc + " -> " + (startpc + length)
					+ ",NI:" + constantPool[nameIndex].value + ",DI:"
					+ constantPool[descriptorIndex].value + ",Slot:" + index);
		}

	}

	// u2:number_of_exceptions,u2:exception_index_table
	private static void parseAttributeException(DataInputStream in)
			throws IOException {
		int numOfExcep = in.readUnsignedShort();
		for (int i = 0; i < numOfExcep; i++) {
			int index = in.readUnsignedShort();
			int classIndex = constantPool[index].classIndex;
			System.out.println("  #" + classIndex + ","
					+ constantPool[classIndex].value);
		}

	}

	// u2:line_number_table_length,line_number_info(u2:start_pc,u2:line_number):line_number_table
	private static void parseAttributeLineNumberTable(DataInputStream in)
			throws IOException {
		int length = in.readUnsignedShort();
		for (int i = 0; i < length; i++) {
			int startpc = in.readUnsignedShort();// 字节码行号
			int lineNumber = in.readUnsignedShort();// java源代码行号
			System.out.println("\tline " + lineNumber + ": " + startpc);
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
	private static void parseAttributeCode(DataInputStream in)
			throws IOException {

		int maxStack = in.readUnsignedShort();
		int maxLocals = in.readUnsignedShort();
		System.out.println("\tstack=" + maxStack + ",locals=" + maxLocals);

		int codeLength = in.readInt();
		for (int i = 0; i < codeLength; i++) {
			int code = in.readUnsignedByte();
			OperateCode opCode = OperateCodes.opCodes[code];
			int skip = parseOperateCode(opCode, in, i);
			i += skip;

		}

		int exceptionTableLength = in.readUnsignedShort();
		for (int i = 0; i < exceptionTableLength; i++) {
			int startPc = in.readUnsignedShort();
			int endPc = in.readUnsignedShort();
			int handlePc = in.readUnsignedShort();
			int catchPc = in.readUnsignedShort();
		}

		parseAttributes(in);
	}

	// 解析字符码指令
	private static int parseOperateCode(OperateCode opCode, DataInputStream in,
			int currentIndex) throws IOException {
		int skip = 0;
		int startIndex = currentIndex;
		String mnemonic = opCode.getMnemonic();

		if (opCode.getOperandType() != null) {

			int operandCount = opCode.getOperandCount();
			switch (opCode.getOperandType()) {
			case CONSTANT_POOL_INDEX:
				byte[] operand = new byte[operandCount];
				in.read(operand, 0, operandCount);
				int index = bytes2int(operand);
				mnemonic += "  #" + index + "(CP)";
				skip += operandCount;

				if (opCode.getOperandType1() != null) {
					if (opCode.getOperandType1() == OperandType.UNSIGNED_BYTE
							|| opCode.getOperandType1() == OperandType.ZERO) {
						int value = in.readUnsignedByte();
						mnemonic += "," + value;
						skip++;
					}

					if (opCode.getOperandType2() != null) {
						if (opCode.getOperandType1() == OperandType.UNSIGNED_BYTE
								|| opCode.getOperandType1() == OperandType.ZERO) {
							int value = in.readUnsignedByte();
							mnemonic += "," + value;
							skip++;
						}
					}
				}

				break;
			case LOCAL_VARIABLES_INDEX:
				int lvIndex = in.readUnsignedShort();
				mnemonic += "  #" + lvIndex + "(LV)";
				skip += 2;
				break;
			case BRANCH_OFFSET:
				int offset = in.readShort();
				mnemonic += "  #" + offset + "(BO)";
				skip += 2;
				break;
			case INT_VALUE:
				byte[] operandInt = new byte[operandCount];
				in.read(operandInt, 0, operandCount);
				int value = bytes2int(operandInt);
				mnemonic += "  " + value;
				skip += operandCount;
				break;
			case INDEX_CONST:
				int u1 = in.readUnsignedByte();
				int constValue = in.readByte();
				mnemonic += "  #" + u1 + "(LV) " + constValue;
				skip += 2;
				break;
			case MUTABLE:
				if ("tableswitch".equals(opCode.getMnemonic())) {
					// TODO:
					mnemonic += "  %mutable";
				} else if ("lookupswitch".equals(opCode.getMnemonic())) {
					// TODO:
					mnemonic += "  %mutable";
				} else if ("wide".equals(opCode.getMnemonic())) {
					// TODO:
					mnemonic += "  %mutable";
				}

				break;
			case ZERO:
			case UNSIGNED_BYTE:
				break;

			}
		}

		System.out.println("\t" + startIndex + " : " + mnemonic);
		return skip;

	}

	private static int bytes2int(byte[] bs) {
		int result = bs[bs.length - 1];
		for (int i = 0; i < bs.length - 1; i++) {
			result = result | (bs[i] << (int) Math.pow(2, bs.length - i - 1));
		}

		return result;
	}
}
