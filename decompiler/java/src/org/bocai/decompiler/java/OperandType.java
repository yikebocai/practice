package org.bocai.decompiler.java;
 
public enum OperandType {
	CONSTANT_POOL_INDEX, // 常量池索引,无符号整数
	LOCAL_VARIABLES_INDEX, // 本地变量索引,无符号整数
	INT_VALUE,// 有符号整数
	UNSIGNED_BYTE,//无符号字节
	ZERO,//
	BRACH_OFFSET, //有符号整数
	INDEX_CONST,//index为u1的本地变量索引，const为有符号的一个字节的整数
	MUTABLE,//操作数不固定，根据指令来确定，比如lookupswitch
	; 

}
