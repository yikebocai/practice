package org.bocai.decompiler.java;

public enum AttributeType {
	Code, // java代码编译成的字节码指令
	ConstantValue, // final关键字定义的常量值
	Deprecated, // 被声明为deprecated的方法和字段
	Exceptions, // 方法抛出的异常
	InnerClass, // 内部类列表
	LineNumberTable, // java源码的行号与字节码指令的对应关系
	LocalVariableTable, // 方法的局部变量描述
	SourceFile, // 源文件名称
	Synthetic// 标识方法或字段为编译器自动生成的
}
