package com.wf.seeker.mytest_cases.testJavaCallBat;

import java.io.File;

import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
// @RestController
@Slf4j
public class TestController {
	@GetMapping("/test")
	public String test() {
		String path = System.getProperty("user.dir").replace("callbat", "");
		log.info("path = " + path);
		try {
			Process child = Runtime.getRuntime().exec("cmd /c test.bat", null, new File(path));
			child.waitFor();
		} catch (Exception e) {
			log.error("执行 test.bat异常", e);
		}
		log.info("callCmd execute finished");
		return "callCmd execute finished";
	}
}
