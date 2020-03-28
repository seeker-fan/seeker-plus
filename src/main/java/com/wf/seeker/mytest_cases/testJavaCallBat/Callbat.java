package com.wf.seeker.mytest_cases.testJavaCallBat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
// @Component
@Slf4j
public class Callbat {

	@Value("${base.path}")
	private String basePath;

	@Scheduled(cron = "${task.cron}")
	public void start() {
		try {
			log.info("execute bat begin，time is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Process child1 = Runtime.getRuntime().exec("cmd /c DNAF_FPD.bat", null, new File(basePath));
			child1.waitFor();
			log.info(basePath + "DNAF_FPD.bat callCmd execute finished");
			Process child2 = Runtime.getRuntime().exec("cmd /c DNAF_FPM.bat", null, new File(basePath));
			child2.waitFor();
			log.info(basePath + "DNAF_FPM.bat callCmd execute finished");
			// Process child1 = Runtime.getRuntime().exec("cmd /c test.bat", null, new File(basePath));
			// child1.waitFor();
			// log.info(basePath + "DNAF_FPD.bat callCmd execute finished");
		} catch (Exception e) {
			log.error("执行bat发生异常：", e);
		}
	}

}
