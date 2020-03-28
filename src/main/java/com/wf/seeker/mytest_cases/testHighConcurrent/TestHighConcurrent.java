package com.wf.seeker.mytest_cases.testHighConcurrent;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试并发
 * 
 * @author Fan.W
 * @since 1.7
 */
public class TestHighConcurrent implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(TestHighConcurrent.class);

	private CountDownLatch single;

	public static int num = 20;

	public TestHighConcurrent(CountDownLatch single) {
		super();
		this.single = single;
	}

	@Override
	public void run() {
		try {
			single.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 此处写并发业务逻辑TODO
		System.out.println(Thread.currentThread().getName());
	}

	public static void main(String[] args) {
		CountDownLatch single = new CountDownLatch(1);// 调用1次countDown() 方法才释放线程。计数器減一 所有线程释放 同时跑
		TestHighConcurrent test = null;
		for (int i = 0; i < num; i++) {
			test = new TestHighConcurrent(single);
			Thread t = new Thread(test);
			t.start();
		}
		System.out.println("并发数：=============" + num);
		single.countDown();
	}
}
