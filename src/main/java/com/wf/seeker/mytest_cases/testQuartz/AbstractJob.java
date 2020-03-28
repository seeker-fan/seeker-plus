package com.wf.seeker.mytest_cases.testQuartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 统一处理异常（发送短信通知）/登记定时任务执行日志TODO
 * 
 * @author Fan.W
 * @since 1.8
 */
@Component
@Slf4j
public abstract class AbstractJob implements ImpJob, Job {
	@Autowired
	protected StringRedisTemplate redis;

	protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 执行报错，通知admin
	 */
	protected static String smsNoticeAdmin;
	/**
	 * 短信url
	 */
	protected static String smsUrl;
	/**
	 * 短信模板
	 */
	protected static String smsTemplate;

	/**
	 * 短信开关
	 */
	protected static String smsSwitch;
	/**
	 * 用redis来控制2小时发送一次短信
	 */
	protected static final String NOTICE_KEY = "IMP:TASK:NOTICE:%s";

	// static {
	// try {
	// smsTemplate = new String(PropertiesUtil.instance().getSms().getTemplate().getBytes("ISO-8859-1"));
	// } catch (UnsupportedEncodingException e) {
	// }
	// smsSwitch = PropertiesUtil.instance().getSms().getSmsSwitch();
	// smsNoticeAdmin = PropertiesUtil.instance().getSms().getNoticeAdmin();
	// }

	/**
	 * 定时任务执行统一入口
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String keyName = context.getJobDetail().getKey().getName();

		try {
			log.info("【key】 {} 开始执行了 【开始时间】  {}", keyName, format.format(new Date()));
			run(context);
			log.info("【key】 {} 执行结束了 【结束时间】  {}", keyName, format.format(new Date()));
		} catch (Exception e) {
			log.error("【key】 {} 执行报错了 【报错内容】  {}", keyName, e);
			// 如果发生异常，半小时通知管理员一次
			String redisKey = String.format(NOTICE_KEY, keyName);
			// @formatter:off 短信开关
//			if ("1".equals(smsSwitch) && StringUtils.isEmpty(redis.opsForValue().get(redisKey))) {
//				SmsUtils.sendToOneTarget(smsUrl, new SmsOneTarget(smsNoticeAdmin,String.format("时间 [%s] 定时任务 [%s] 执行报错了 报错内容  [%s]",format.format(new Date()) ,keyName, e.getMessage())));
//				redis.opsForValue().set(redisKey, "1", 2, TimeUnit.HOURS);
//			}
			// @formatter:on
		}
	}
}
