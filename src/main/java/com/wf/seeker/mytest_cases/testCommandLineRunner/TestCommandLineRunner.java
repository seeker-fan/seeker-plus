package com.wf.seeker.mytest_cases.testCommandLineRunner;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * springboot启动时执行任务CommandLineRunner
 * 
 * @author Fan.W
 * @since 1.8
 */
@Component
@Slf4j
public class TestCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// log.info("seeker-plus 正在启动，TestCommandLineRunner 开始执行任务了！！！");

		if (args != null && args.length > 0) {
			List<String> list = Arrays.asList(args);
			list.stream().forEach(System.out::println);
		}

	}

}
