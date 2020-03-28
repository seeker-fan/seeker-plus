package com.wf.seeker.mytest_cases.testRabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 需要将SeekerPlusApplication上注解打开才能收到消息
 * 
 * @title
 * @description
 * @since JDK1.8
 */
public class SeekerListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		String reqMsg = new String(message.getBody());

		System.out.println(reqMsg);
	}
}
