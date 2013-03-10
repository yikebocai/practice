package org.bocai.decompiler.java;

public interface MethodAccessFlag {
	// 字符是否为public
	int ACC_PUBLIC = 0x0001;
	int ACC_PRIVATE = 0x0002;
	int ACC_PROTECTED = 0x0004;
	int ACC_STATIC = 0x0008;
	int ACC_FINAL = 0x0010;
	int ACC_SYNCHRONIZED = 0x0020;
	// 方法是否是由编译器产生的桥接方法
	int ACC_BRIDGE = 0x0040;
	// 方法是否接受不定参数
	int ACC_VARARGS = 0x0080;
	int ACC_NATIVE = 0x0100;
	int ACC_ABSTRACT = 0x0400;
	// 方法是否为strictfp
	int ACC_STRICT = 0x0800;
	// 字段是否由编译器自动产生
	int ACC_SYNTHETIC = 0x1000;
}
