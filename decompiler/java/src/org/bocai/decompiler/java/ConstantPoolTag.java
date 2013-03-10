package org.bocai.decompiler.java;

public interface ConstantPoolTag {
	//UTF-8编码的字符串
	int CONSTNAT_Utf8_info=1;
	//整形字面量
	int CONSTNAT_Integer_info=3;
	int CONSTNAT_Float_info=4;
	int CONSTNAT_Long_info=5;
	int CONSTNAT_Double_info=6;
	//类或接口的符号引用
	int CONSTNAT_Class_info=7;
	//字符串类型字面量
	int CONSTNAT_String_info=8;
	//字段的符号引用
	int CONSTNAT_Fieldref_info=9;
	//类中方法的符号引用
	int CONSTNAT_Methodref_info=10;
	//接口中方法的符号引用
	int CONSTNAT_InterfaceMethodref_info=11;
	//字段或方法的部分符号引用
	int CONSTNAT_NameAndType_info=12;
}
