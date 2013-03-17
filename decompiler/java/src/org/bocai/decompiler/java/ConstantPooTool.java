package org.bocai.decompiler.java;

public class ConstantPooTool {

	public static String getString(ConstantPoolObject obj,ConstantPoolObject[] constantPool){
		String result=null;
		
		switch (obj.tag) {
		case ConstantPoolTag.CONSTNAT_Utf8_info:
		case ConstantPoolTag.CONSTNAT_Integer_info:
		case ConstantPoolTag.CONSTNAT_Float_info:
		case ConstantPoolTag.CONSTNAT_Long_info:
		case ConstantPoolTag.CONSTNAT_Double_info:
		case ConstantPoolTag.CONSTNAT_String_info:
			result=(String)obj.value;
			break;
		case ConstantPoolTag.CONSTNAT_Class_info:
			result="Class "+(String)constantPool[(int)obj.nameIndex].value;
			break;
			 
		case ConstantPoolTag.CONSTNAT_Fieldref_info:
		case ConstantPoolTag.CONSTNAT_Methodref_info:
		case ConstantPoolTag.CONSTNAT_InterfaceMethodref_info:
			int classIndex = obj.classIndex;
			int nameAndTypeIndex = obj.nameAndTypeIndex;
			 
			String type = null;
			if (obj.tag == ConstantPoolTag.CONSTNAT_Fieldref_info) {
				type = "Fieldref";
			} else if (obj.tag == ConstantPoolTag.CONSTNAT_Methodref_info) {
				type = "Methodref";
			} else {
				type = "InterfaceMethodref";
			}

			String clazz = (String)constantPool[(int)classIndex].value;
			ConstantPoolObject constantPoolObject = constantPool[(int)nameAndTypeIndex];
			String name=(String)constantPool[(int)constantPoolObject.classIndex].value;
			String nameType=(String)constantPool[(int)constantPoolObject.descriptorIndex].value;
			result=type+" "+clazz+" "+name+":"+nameType;
			break;
		case ConstantPoolTag.CONSTNAT_NameAndType_info:
			int classIndex2 = obj.classIndex;
			int descriptorIndex = obj.descriptorIndex;
			String name2=(String)constantPool[classIndex2].value;
			String nameType2=(String)constantPool[descriptorIndex].value;
			result="NameAndType "+name2+":"+nameType2;
			break;
		}
		
		return result;
	}
}
