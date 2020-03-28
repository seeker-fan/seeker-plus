package com.wf.seeker.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
public class RestTemplateUtils {
	/** 日志工具 */
	private static Logger log = LoggerFactory.getLogger(RestTemplateUtils.class);

	/**
	 * RestTemplate发送http post请求
	 * 
	 * @param url 调用服务地址
	 * @param reqMap 请求参数(转为json发送)
	 * @return
	 *
	 * @authz Fan.W
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> sendPost(String url, Map<String, String> reqMap) {

		String reqBody = JsonUtil.objToJson(reqMap);
		log.debug("HTTP JSON POST 请求报文: " + reqBody);
		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		// 设置字符集和请求头参数
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		HttpEntity<String> entity = new HttpEntity<String>(reqBody, headers);

		template.setErrorHandler(new DefaultResponseErrorHandler());

		ResponseEntity<String> rspMsg = template.postForEntity(url, entity, String.class);
		// 获取响应信息
		String rspBody = rspMsg.getBody();

		log.debug("响应报文：" + rspBody);

		Map<String, String> rspMap = new HashMap<>();

		rspMap = JsonUtil.jsonToObj(rspBody, HashMap.class);
		return rspMap;
	}

	private static void sendPost(String url, String reqBody) {
		// 设置连接超时和响应超时
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		requestFactory.setConnectTimeout(60000);
		// 响应超时3分钟
		requestFactory.setReadTimeout(180000);

		RestTemplate template = new RestTemplate(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		// 设置字符集
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		HttpEntity<String> entity = new HttpEntity<String>(reqBody, headers);

		template.setErrorHandler(new DefaultResponseErrorHandler());

		log.info("请求短信服务，url={},reqBody={}", url, reqBody);
		// 发送post请求
		ResponseEntity<String> rspMsg = template.postForEntity(url, entity, String.class);
		int statusCode = rspMsg.getStatusCodeValue();
		log.info("响应码：{}", statusCode);

		HttpHeaders rspHeaders = rspMsg.getHeaders();
		log.info("响应头：{}", JsonUtil.objToJson(rspHeaders));
		// 获取响应信息
		String rspBody = rspMsg.getBody();
		log.info("响应报文：{}", rspBody);

	}
}
