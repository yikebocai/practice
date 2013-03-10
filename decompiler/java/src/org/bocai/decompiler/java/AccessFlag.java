package org.bocai.decompiler.java;

public interface AccessFlag {
	// 是否为public类型
	int ACC_PUBLIC = 0x0001;
	// 是否被声明为final,只有类可以设置
	int ACC_FINAL = 0x0010;
	// 是否允许使用invokespecial字节码指令,jdk1.2后都为真
	int ACC_SUPER = 0x0020;
	// 标识这是一个接口
	int ACC_INTERFACE = 0x0200;
	// 是否为为abstract类型,对抽象类或接口为真,其它为假
	int ACC_ABSTRACT = 0x0400;
	// 标识这个类并非由用户代码产生
	int ACC_SYNTHETIC = 0x1000;
	// 标识这是一个注解
	int ACC_ANNOTATION = 0x2000;
	// 标识这是一个枚举
	int ACC_ENUM = 0x4000;
}
