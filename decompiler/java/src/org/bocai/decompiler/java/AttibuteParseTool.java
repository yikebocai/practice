package org.bocai.decompiler.java;

import java.io.DataInputStream;
import java.io.IOException;

public class AttibuteParseTool {

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
	public static void parseAttributeCode(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {

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
		if (exceptionTableLength > 0) {
			System.out.println("  Exception table:");
			System.out.println("\tfrom\tto\ttarget\tClass");
			for (int i = 0; i < exceptionTableLength; i++) {
				int startPc = in.readUnsignedShort();
				int endPc = in.readUnsignedShort();
				int handlePc = in.readUnsignedShort();
				int catchType = in.readUnsignedShort();

				ConstantPoolObject obj = constantPool[catchType];
				String clazz = ConstantPooTool.getString(obj, constantPool);
				System.out.println("\t" + startPc + "\t" + endPc + "\t"
						+ handlePc + "\t" + clazz);

			}
		}
		ClassParseTool.parseAttributes(in, constantPool);
	}

	public static void parseAttributeConstantValue(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
		int index = in.readUnsignedShort();
		System.out.println("\t#" + index + "  //" + constantPool[index].tag
				+ " " + constantPool[index].value);

	}

	public static void parseAttributeDeprecated(DataInputStream in) {
		System.out.println("Deprecated:true");

	}

	// u2:number_of_exceptions,u2:exception_index_table
	public static void parseAttributeException(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
		int numOfExcep = in.readUnsignedShort();
		for (int i = 0; i < numOfExcep; i++) {
			int index = in.readUnsignedShort();
			int classIndex = constantPool[index].classIndex;
			System.out.println("  #" + classIndex + ","
					+ constantPool[classIndex].value);
		}

	}

	public static void parseAttributeInnerClass(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
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

	// u2:line_number_table_length,line_number_info(u2:start_pc,u2:line_number):line_number_table
	public static void parseAttributeLineNumberTable(DataInputStream in)
			throws IOException {
		int length = in.readUnsignedShort();
		for (int i = 0; i < length; i++) {
			int startpc = in.readUnsignedShort();// 字节码行号
			int lineNumber = in.readUnsignedShort();// java源代码行号
			System.out.println("\tline " + lineNumber + ": " + startpc);
		}

	}

	// u2:local_variable_table_length,local_variable_info:local_variable_table
	public static void parseAttributeLocalVariableTable(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
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

	public static void parseAttributeSouceFile(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
		int index = in.readUnsignedShort();
		System.out.println("\t#" + index + "  //" + constantPool[index].value);
	}

	// u2:number_of_entries,stack_map_frame: entries[number_of_entries];
	public static void parseAttributeStackMapTable(DataInputStream in,
			ConstantPoolObject[] constantPool) throws IOException {
		int number = in.readUnsignedShort();
		System.out.println("\tnumber_of_entries = " + number);

		for (int i = 0; i < number; i++) {
			int frameType = in.readUnsignedByte();
			String frameTypeTag = null;

			if (frameType < 64) {
				frameTypeTag = "SAME";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");
			} else if (frameType >= 64 && frameType < 128) {
				frameTypeTag = "SAME_LOCALS_1_STACK_ITEM";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");

				String stack = "[";
				stack += VerificationTypeInfoTool.verify(in, constantPool);
				stack += "]";
				System.out.println("\t\tstack = " + stack);
			} else if (frameType >= 128 && frameType < 247) {
				frameTypeTag = "SAME_LOCALS_1_STACK_ITEM_EXTENDED";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");

				int offsetDelta = in.readUnsignedShort();
				System.out.println("\t\toffset_delta = " + offsetDelta);

				String stack = "[";
				stack += VerificationTypeInfoTool.verify(in, constantPool);
				stack += "]";
				System.out.println("\t\tstack = " + stack);
			} else if (frameType == 247) {
				frameTypeTag = "CHOP";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");

				int offsetDelta = in.readUnsignedShort();
				System.out.println("\t\toffset_delta = " + offsetDelta);
			} else if (frameType >= 248 && frameType < 251) {
				frameTypeTag = "SAME_FRAME_EXTENDED";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");

				int offsetDelta = in.readUnsignedShort();
				System.out.println("\t\toffset_delta = " + offsetDelta);
			} else if (frameType >= 252 && frameType < 254) {
				frameTypeTag = "APPEND";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");

				int offsetDelta = in.readUnsignedShort();
				System.out.println("\t\toffset_delta = " + offsetDelta);

				String locals = "[";
				int numberOfLocals = frameType - 251;
				for (int j = 0; j < numberOfLocals; j++) {
					locals += VerificationTypeInfoTool.verify(in, constantPool);
				}
				locals += "]";
				System.out.println("\t\tlocals = " + locals);
			} else if (frameType == 255) {
				frameTypeTag = "FULL_FRAME";
				System.out.println("\tframe_type=" + frameType + "  /*"
						+ frameTypeTag + "*/");

				int offsetDelta = in.readUnsignedShort();
				System.out.println("\t\toffset_delta = " + offsetDelta);

				String locals = "[";
				int numberOfLocals = in.readUnsignedShort();
				for (int j = 0; j < numberOfLocals; j++) {
					locals += VerificationTypeInfoTool.verify(in, constantPool);
				}
				locals += "]";
				System.out.println("\t\tlocals = " + locals);

				String stack = "[";
				int numberOfStack = in.readUnsignedShort();
				for (int j = 0; j < numberOfStack; j++) {
					stack += VerificationTypeInfoTool.verify(in, constantPool);
				}
				stack += "]";
				System.out.println("\t\tstack = " + stack);

			}

		}

	}

	public static void parseAttributeSynthetic(DataInputStream in) {
		System.out.println("Synthetic:true");

	}

	// 解析字符码指令
	public static int parseOperateCode(OperateCode opCode, DataInputStream in,
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
