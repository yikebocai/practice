import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 对java class文件进行反编译
 * @author xinbo.zhangxb
 *
 */
public class Dec {
	private static final String magicNum = "CAFEBABE";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String filename = "d:\\work\\jvm\\testfiles\\Helloworld.class";
		DataInputStream in = new DataInputStream(new FileInputStream(filename));
		
		//magic number 
		int b = 0;
		String magic = "";
		for (int i = 0; i < 4; i++) {
			b = in.readUnsignedByte();
			magic += Integer.toHexString(b).toUpperCase();
		}

		if (magicNum.equals(magic)) {
			System.out.println("This is a JVM class file");
		}

		//version
		int minorVersion = in.readUnsignedShort();
		int majorVersion = in.readUnsignedShort();
		System.out.println("class version:" + majorVersion + "." + minorVersion);
		in.close();

	}

}
