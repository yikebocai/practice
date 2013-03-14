import java.io.FileInputStream;
import java.io.IOException;

public final class Helloworld{
        private int age=30;
	private double weight=63.5;
	private Long time=System.currentTimeMillis();
        private  static int level=7;

	public static void main(String[] args){
	   int offset=1;

	   try{
	   level++;
           System.out.println("level:"+level);
           
	   new FileInputStream("test");
	   
	   long result=10000000;
	   result+=offset;
	   System.out.println("result:"+result);
           
	   }catch(IOException e){
              e.printStackTrace();
	   }
	}
}
