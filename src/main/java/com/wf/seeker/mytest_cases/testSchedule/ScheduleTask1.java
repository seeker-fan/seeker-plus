package com.wf.seeker.mytest_cases.testSchedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
// @Component
public class ScheduleTask1 {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleTask1.class);
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

	// @Scheduled(fixedDelay = 3000)
	public void testSchedule1() {
		logger.debug(" the time is {}", format.format(new Date()));
	}

	// @Scheduled(cron = "0/1 * * * * ?")
	public void testSchedule2() {
		logger.debug(" the beijing time is {} !!!", format.format(new Date()));
	}

}
