package org.bocai.decompiler.java;

/**
 * 表示常量池中的数据对象
 * 
 * @author yikebocai@gmail.com
 * 
 */
public class ConstantPoolObject {
	int tag;
	Object value;
	int nameIndex;
	int stringIndex;
	int classIndex;
	int nameAndTypeIndex;
	int descriptorIndex;
}
