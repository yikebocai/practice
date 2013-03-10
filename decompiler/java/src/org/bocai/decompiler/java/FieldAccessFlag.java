package org.bocai.decompiler.java;

public interface FieldAccessFlag {
	// 字符是否为public
	int ACC_PUBLIC = 0x0001;
	int ACC_PRIVATE = 0x0002;
	int ACC_PROTECTED = 0x0004;
	int ACC_STATIC = 0x0008;
	int ACC_FINAL = 0x0010;
	int ACC_VOLATILE = 0x0040;
	int ACC_TRANSIENT = 0x0080;
	// 字段是否由编译器自动产生
	int ACC_SYNTHETIC = 0x1000;
	int ACC_ENUM = 0x4000;
}
