package com.wf.seeker.mytest_cases.testRedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wf.seeker.util.RedisUtils;

/**
 * redis 对账
 * 
 * @author Fan.W
 * @since 1.8
 */
@Controller
public class RedisCheckAccountController {
	public static final Logger logger = LoggerFactory.getLogger(RedisCheckAccountController.class);

	// @RequestMapping(value = "/redistest")
	// @ResponseBody
	// public void redistest() {
	// Set<String> keys = RedisUtils.keys();
	// for (String string : keys) {
	// System.out.println("key : " + string);
	// }
	//
	// long s = RedisUtils.decr("name", 12);
	// logger.debug(s + "");
	// }

	public static final String INNER_KEY = "inner";
	public static final String OUTER_KEY = "outer";
	public static final String INTERSECT = "intersect";
	public static final String INNER_DIFF = "inner_diff";
	public static final String OUTER_DIFF = "outer_diff";

	@RequestMapping(value = "/redischeckaccount")
	@ResponseBody
	public void redischeckaccount() {

		// 准备电子账户订单集合
		List<String> innerOrders = new ArrayList<>();

		String innerOrder = "";
		for (int i = 0; i < 100000; i++) {
			innerOrder = "order" + i + "_" + "金额" + i;
			innerOrders.add(innerOrder);
		}

		innerOrders.add("order99_99");
		innerOrders.add("order88_88");
		innerOrders.add("order77_77");
		innerOrders.add("order66_66");

		// 准备居士支付订单集合
		List<String> outerOrders = new ArrayList<>();

		String outerOrder = "";
		for (int i = 0; i < 100000; i++) {
			outerOrder = "order" + i + "_" + "金额" + i;
			outerOrders.add(outerOrder);
		}
		outerOrders.add("order55_55");
		outerOrders.add("order44_44");
		outerOrders.add("order33_33");
		outerOrders.add("order22_22");

		long startTime = System.currentTimeMillis();

		logger.debug("redis 对账开始============================");

		RedisUtils.sadd(INNER_KEY, innerOrders);
		RedisUtils.sadd(OUTER_KEY, outerOrders);

		RedisUtils.sinterAndStore(INTERSECT, INNER_KEY, OUTER_KEY);

		RedisUtils.sdiffAndStore(INNER_KEY, INTERSECT, INNER_DIFF);

		RedisUtils.sdiffAndStore(OUTER_KEY, INTERSECT, OUTER_DIFF);

		// Set<String> sinter = RedisUtils.smembers(INTERSECT);
		// logger.debug("双方订单交集如下： ");
		// for (String string : sinter) {
		// logger.debug(string);
		// }

		Set<String> innerDiffSet = RedisUtils.smembers(INNER_DIFF);
		Set<String> outerDiffSet = RedisUtils.smembers(OUTER_DIFF);

		logger.debug("我方多余订单如下： ");
		for (String string : innerDiffSet) {
			logger.debug(string);
		}

		logger.debug("对方多余订单如下： ");
		for (String string : outerDiffSet) {
			logger.debug(string);
		}

		long endTime = System.currentTimeMillis();
		logger.debug("redis 对账结束============================ ");

		logger.debug("对账耗时: " + (endTime - startTime + "毫秒"));
	}
}
