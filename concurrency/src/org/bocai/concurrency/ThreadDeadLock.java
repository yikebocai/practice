package org.bocai.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadLock {
	static ExecutorService service = Executors.newSingleThreadExecutor();

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		RenderPageTask task=new RenderPageTask(service);
		Future<String> html=service.submit(task);
		
		System.out.println(html.get());
	}
}

class RenderPageTask implements Callable<String> {
	ExecutorService service;
	
	public RenderPageTask(ExecutorService service){
		this.service=service;
	}
	
	@Override
	public String call() throws Exception {
		Future<String> header, footer;
		header = service.submit(new LoadFileTask("header.html"));
		footer = service.submit(new LoadFileTask("footer.html"));
		String page = renderBody();

		String html = header.get() + page + footer.get();
		return html;
	}
	
	// mock render body
	private static String renderBody() {
		return "<body>test</body>";
	}
	
}


class LoadFileTask implements Callable<String> {
	private String file;

	public LoadFileTask(String file) {
		this.file = file;
	}

	@Override
	public String call() throws Exception {
		//Thread.sleep((int) (Math.random() * 10000));
		return "<test>aaa</test>";
	}

}
