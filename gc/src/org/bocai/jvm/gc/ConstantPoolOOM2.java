package org.bocai.jvm.gc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

/**
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class ConstantPoolOOM2 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClazz = pool.get("org.bocai.jvm.gc.TestBean");

		// ctClazz.defrost();

		// add field sample
		CtField field = new CtField(pool.get("java.lang.String"), "test",
				ctClazz);
		ctClazz.addField(field);
		for (int i = 1; i < 10000; i++) {
			CtField field2 = new CtField(pool.get("java.lang.String"), "test1"
					+ i, ctClazz);
			ctClazz.addField(field2);
		}

		// add method sample
		CtClass[] paramTypes = { pool.get("java.lang.String") };
		CtMethod ctMethod = new CtMethod(pool.get("java.lang.String"),
				"getTest", paramTypes, ctClazz);
		ctMethod.setBody("{ return  $1 ;}");
		ctClazz.addMethod(ctMethod);

		Class<TestBean> clazz = ctClazz.toClass();

		// list fields
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			System.out.println("Field: " + f.getName());
		}

		// list methods
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			System.out.println("Method: " + m.getName());
		}

		// invoke method
		Method method = clazz.getMethod("getTest" ,
				new Class[] { String.class });
		TestBean bean = clazz.newInstance();
		String invoke = (String) method.invoke(bean,
				new Object[] { "parameter1" });
		System.out.println(invoke);

	}

}

class TestBean {

}
