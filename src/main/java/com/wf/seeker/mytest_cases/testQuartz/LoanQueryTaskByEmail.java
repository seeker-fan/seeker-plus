package com.wf.seeker.mytest_cases.testQuartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@formatter:off
/**
 * 需求：库存放款指令发送给银行后，及时通过邮件或邮件通知主机厂。
 * 定时任务：库存放款记录查询：每天8:00~20:00之间，每60秒查询有无库存放款记录产生。
 * 任务：库存放款通知：有库存放款记录时启动，对于当次库存放款数据发送通知到主机厂联系人。
 * @author Fan.W
 * @since 1.8
 */
//@formatter:on
@Component
@Slf4j
@DisallowConcurrentExecution
// quartz默认设置是并发的：job A，每2分钟执行一次，但是job本身就要执行5分钟，这个时候，所以它又会开一个线程来执行
public class LoanQueryTaskByEmail extends AbstractJob {

	// /**
	// * 邮件模板
	// */
	// private static String emailTemplate;
	//
	// /**
	// * 邮件url
	// */
	// private static String emailUrl;
	// /**
	// * 邮件主题
	// */
	// private static String emailTitle;
	//
	// /**
	// * 邮件开关
	// */
	// private static String emailSwitch;
	//
	// static {
	// try {
	// emailTemplate = new String(PropertiesUtil.instance().getEmail().getTemplate().getBytes("ISO-8859-1"));
	// } catch (UnsupportedEncodingException e) {
	// }
	// emailTitle = PropertiesUtil.instance().getEmail().getTitle();
	// emailUrl = PropertiesUtil.instance().getEmail().getUrl();
	// emailSwitch = PropertiesUtil.instance().getEmail().getEmailSwitch();
	// }

	@Override
	public void run(JobExecutionContext context) throws Exception {
	}

}
