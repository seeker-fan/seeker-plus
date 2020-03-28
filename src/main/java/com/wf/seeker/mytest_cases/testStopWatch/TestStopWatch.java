package com.wf.seeker.mytest_cases.testStopWatch;

import org.springframework.util.StopWatch;

/**
 * 记录代码执行时间
 * 
 * @author Fan.W
 * @since 1.8
 */
public class TestStopWatch {
	private void test() throws InterruptedException {
		StopWatch sw = new StopWatch();

		sw.start("起床");
		Thread.sleep(1000);
		sw.stop();

		sw.start("洗漱");
		Thread.sleep(2000);
		sw.stop();

		sw.start("锁门");
		Thread.sleep(500);
		sw.stop();

		// System.out.println(sw.prettyPrint());
		System.out.println(sw.getTotalTimeMillis());
		System.out.println(sw.getLastTaskName());
		System.out.println(sw.getLastTaskInfo());
		System.out.println(sw.getTaskCount());
	}

	public static void main(String[] argv) throws InterruptedException {
		TestStopWatch testStopWatch = new TestStopWatch();
		testStopWatch.test();
	}
}
