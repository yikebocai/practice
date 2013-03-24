package org.bocai.jvm.gc;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author yikebocai@gmail.com
 * @since 2013-3-24
 * 
 */
public class MethodAreaOOM {

	public static void main(String[] args) {
		while (true) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				public Object intercept(Object obj, Method method,
						Object[] args, MethodProxy proxy) throws Throwable {
					System.out.println("insert before");
					return proxy.invokeSuper(obj, args);
				}

			});
			
			OOMObject obj=(OOMObject)enhancer.create();
			obj.test();
		}

	}

	static class OOMObject {
		public void test(){
			System.out.println("This is super class method");
		}
	}

}
