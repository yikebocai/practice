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
	Synthetic,// 标识方法或字段为编译器自动生成的
	StackMapTable,//为了加快Class文件的校验速度，把类型校验时需求用到的相关信息直接写入到Class文件中
	EnclosingMethod,//当一个类为局部类或匿名类时,可通过这个属性来声明其访问范围
	Signature,//存储类,方法,字段的特征签名
	SourcingDebugException,//存储额外的调试信息,比如非java语言编译成的字节码
	LocalVariableTypeTable,//使用特征签名代替描述符
	RuntimeVisibleAnnotions,//指明哪些注解是运行时可见的
	RuntimeInvisibleAnnotions,
	RuntimeVisibleParameterAnnotations,//
	RuntimeInvisibleParameterAnnotations,
	AnnotationDefault //记录注解类元素的默认值 
	;
	
}
