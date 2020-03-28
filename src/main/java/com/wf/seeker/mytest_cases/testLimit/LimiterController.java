package com.wf.seeker.mytest_cases.testLimit;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wf.seeker.common.Limit;

/**
 * 测试接口限流
 * 
 * @author Fan.W
 * @since 1.8
 */
@RestController
public class LimiterController {

	private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

	@Limit(key = "test", period = 5, count = 30)
	@GetMapping("/test")
	// @CurrentLimiter(QPS = 2)
	public int testLimiter() {
		// 意味著 3S 内最多允許訪問10次
		return ATOMIC_INTEGER.incrementAndGet();
	}
}
