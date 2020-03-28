package com.wf.seeker.configure;

import org.springframework.stereotype.Component;

/**
 * @title 配置文件工具类
 * @description 不必自动装入，可静态方法获取属性
 */
@Component
public class PropertiesUtil {

	private static StatisticsProperties statisticsProperties;

	private PropertiesUtil(StatisticsProperties statisticsProperties) {
		PropertiesUtil.statisticsProperties = statisticsProperties;
	}

	public static StatisticsProperties instance() {
		return statisticsProperties;
	}

}
