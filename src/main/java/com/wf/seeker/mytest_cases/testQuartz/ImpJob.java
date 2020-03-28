package com.wf.seeker.mytest_cases.testQuartz;

import org.quartz.JobExecutionContext;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
public interface ImpJob {

	void run(JobExecutionContext context) throws Exception;
}
