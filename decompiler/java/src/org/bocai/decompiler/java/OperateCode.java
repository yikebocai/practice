package org.bocai.decompiler.java;

/**
 * 操作码指令，参考：http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-7.html
 * 
 * @author yikebocai@gmail.com
 * @since 2013-3-11
 * 
 */
public class OperateCode {
	private String mnemonic;
	private OperandType operandType;// 操作数类型
	private int operandCount;// 参数字节数 
	
	private OperandType operandType1;// 操作数类型
	private int operandCount1;// 参数字节数 
	private OperandType operandType2;// 操作数类型
	private int operandCount2;// 参数字节数 

	public OperateCode(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public OperateCode(String mnemonic, OperandType operandType, int operandCount) {
		this.mnemonic = mnemonic;
		this.operandCount = operandCount;
		this.operandType = operandType;
	}

	public OperateCode(String mnemonic, OperandType operandType, int operandCount,OperandType operandType1, int operandCount1) {
		this.mnemonic = mnemonic;
		this.operandCount = operandCount;
		this.operandType = operandType;
		this.operandCount1 = operandCount1;
		this.operandType1 = operandType1;
	}
	
	public OperateCode(String mnemonic, OperandType operandType, int operandCount,OperandType operandType1, int operandCount1,OperandType operandType2, int operandCount2) {
		this.mnemonic = mnemonic;
		this.operandCount = operandCount;
		this.operandType = operandType;
		this.operandCount1 = operandCount1;
		this.operandType1 = operandType1;
		this.operandCount2 = operandCount2;
		this.operandType2 = operandType2;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public OperandType getOperandType() {
		return operandType;
	}

	public void setOperandType(OperandType operandType) {
		this.operandType = operandType;
	}

	public int getOperandCount() {
		return operandCount;
	}

	public void setOperandCount(int operandCount) {
		this.operandCount = operandCount;
	}

	public OperandType getOperandType1() {
		return operandType1;
	}

	public void setOperandType1(OperandType operandType1) {
		this.operandType1 = operandType1;
	}

	public int getOperandCount1() {
		return operandCount1;
	}

	public void setOperandCount1(int operandCount1) {
		this.operandCount1 = operandCount1;
	}

	public OperandType getOperandType2() {
		return operandType2;
	}

	public void setOperandType2(OperandType operandType2) {
		this.operandType2 = operandType2;
	}

	public int getOperandCount2() {
		return operandCount2;
	}

	public void setOperandCount2(int operandCount2) {
		this.operandCount2 = operandCount2;
	}

 
}
