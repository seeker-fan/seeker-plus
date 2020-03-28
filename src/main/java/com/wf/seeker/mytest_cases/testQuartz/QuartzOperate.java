package com.wf.seeker.mytest_cases.testQuartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wf.seeker.entity.SysQuartzJob;
import com.wf.seeker.mapper.SysQuartzJobMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据库层的操作由system项目完成，quartz设置由本类完成 quartz操作请参考：https://www.w3cschool.cn/quartz_doc/quartz_doc-2put2clm.html
 * 
 * @author Fan.W
 * @since 1.8
 */
@Component
@Slf4j
public class QuartzOperate {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SysQuartzJobMapper quartzJobMapper;

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 服务启动时加载所有定时任务
	 */
	@PostConstruct
	private void init() {
		List<SysQuartzJob> jobs = quartzJobMapper.findAllSchedule();
		if (jobs != null && !jobs.isEmpty())
			jobs.stream().forEach(t -> add(t));
	}

	/**
	 * @param id 定时任务id
	 * @param type 操作类型：1 新增 2修改&启用停用 3删除
	 * @throws SchedulerException
	 */
	public String operateQuartzJob(String id, String type) throws SchedulerException {
		SysQuartzJob quartzJob = quartzJobMapper.findConfigById(id);
		if (quartzJob != null) {
			switch (type) {
			case "1":
				add(quartzJob);
				break;
			case "2":
				update(quartzJob);
				break;
			case "3":
				delete(quartzJob);
				break;
			default:
				break;
			}
			return "success";
		} else {
			log.info("根据id = {},未查询到定时任务,无法执行对应操作 type = {} ", id, type);
			return "false";
		}
	}

	/**
	 * 新增定时任务
	 * 
	 * @param quartzJob
	 * @throws SchedulerException
	 */
	//@formatter:off
	private void add(SysQuartzJob quartzJob){
		if(!checkexists(quartzJob.getJobClassName().trim())) {
			this.schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(),quartzJob.getParameter());
			log.info("时间：{}，新增定时任务 ：{}",format.format(new Date()) ,quartzJob);	
		}else {
			log.info("add 定时任务 {} 已经存在",quartzJob.getJobClassName().trim());
		}
		
	}
	
	/**
	 * 校验定时任务是否存在
	 * @param jobClassName
	 * @return
	 * @throws SchedulerException
	 */
	private boolean checkexists(String jobClassName)  {
		try {
			return scheduler.checkExists(JobKey.jobKey(jobClassName));
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改定时任务
	 * @param quartzJob
	 * @throws SchedulerException 
	 */
	private void update(SysQuartzJob quartzJob)  {
		if (1 == (quartzJob.getStatus())) {
			schedulerDelete(quartzJob.getJobClassName().trim());
			schedulerAdd(quartzJob.getJobClassName().trim(), quartzJob.getCronExpression().trim(), quartzJob.getParameter());
		}else{
			try {
				scheduler.pauseJob(JobKey.jobKey(quartzJob.getJobClassName().trim()));
			} catch (SchedulerException e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException("修改定时任务失败");
			}
		}
		log.info("时间：{}，修改定时任务 ：{}",format.format(new Date()) ,quartzJob);

	}
	
	/**
	 * 删除定时任务
	 * @param quartzJob
	 * @throws SchedulerException 
	 */
	private void delete(SysQuartzJob quartzJob)  {
		schedulerDelete(quartzJob.getJobClassName().trim());
		log.info("时间：{}，删除定时任务 ：{}",format.format(new Date()) ,quartzJob);
	}

	public  void schedulerAdd(String jobClassName, String cronExpression, String parameter) {
		try {
			// 启动调度器
			scheduler.start();
			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName).usingJobData("parameter", parameter).build();
			// 表达式调度构建器(即任务执行的时间)
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		
		} catch (Exception e) {
			throw new RuntimeException("后台找不到该类名：" + jobClassName, e);
		}
	}

	private Job getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (Job) class1.newInstance();
	}
	
	public void schedulerDelete(String jobClassName)  {
		if(checkexists(jobClassName)) {
			try {
				scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
				scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
				scheduler.deleteJob(JobKey.jobKey(jobClassName));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException("删除定时任务失败");
			}
		}else {
			log.info("delete 定时任务 {} 不存在",jobClassName);
		}
		
	}
	//@formatter:on
}
