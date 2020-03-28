package com.wf.seeker.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientUtils {

	public static final Logger logger = LoggerFactory.getLogger(HttpClients.class);

	public static ObjectMapper mapper = new ObjectMapper();

	//@formatter:off
	public static Map<String, Object> callJson(String url,Map<String, String> request,Map<String, String> headersMap) {
		String req = "";
		try {
			req = mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			logger.error("map转json换异常", e);
		}
		return post(url, req, headersMap);
	}

	/**
	 * @param url 请求地址
	 * @param request  请求参数String
	 * @param headersMap  请求头参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> post(String url, String request, Map<String, String> headersMap) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		String response = "";
		
		try {
			// httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
			if (!headersMap.isEmpty()) {//头信息
				for (Map.Entry<String, String> entry : headersMap.entrySet()) {
					httppost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			logger.debug("REQUEST[" + url + "]:" + request);
			HttpEntity reqEntity = new ByteArrayEntity(request.getBytes());
			
			httppost.setEntity(reqEntity);
			
			CloseableHttpResponse execute = httpclient.execute(httppost);
			
			int code = execute.getStatusLine().getStatusCode();
			if (code != 200) {
				logger.error("调用目标服务响应失败，响应码：" + code);
			}

			response = EntityUtils.toString(execute.getEntity(), "UTF-8");
			
			Header[] reqHeads = httppost.getAllHeaders();
			for (Header header : reqHeads) {
				logger.debug("请求信息 request  header [name]: " + header.getName() + " request header [value]: "
						+ header.getValue());
			}

			Header[] headList = execute.getAllHeaders();
			for (Header header : headList) {
				logger.debug("响应信息 response header [name]: " + header.getName() + "response  header [value]: "
						+ header.getValue());
			}

			logger.debug("RESPONSE[" + url + "]:" + response);

			return mapper.readValue(response, HashMap.class);
			
		} catch (Exception e) {
			logger.error("通讯异常", e);
			return null;
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("通讯异常", e);
			}
		}
	}
	//@formatter:on

}