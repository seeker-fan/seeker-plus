package com.wf.seeker.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fan.W
 * @since 1.8
 */
public class HttpUtils {
	/** 日志工具 */
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * http get or post common
	 * 
	 * @param context http 请求头信息
	 * @param request 请求参数(map 转 json)
	 * @param url 调用服务地址
	 * @param method 请求方式
	 * @return
	 *
	 * @authz Fan.W
	 */
	public static String call(Map<String, String> context, String request, String url, String method) {
		List<Header> headerList = new ArrayList<Header>();
		Iterator<String> iterator = context.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (context.get(key) == null || context.get(key) instanceof String)
				headerList.add(new Header(key, context.get(key)));
		}
		String result = null;
		if (method.equals("get")) {
			result = get(headerList, url);
		} else {
			result = post(headerList, request, url);
		}
		return result;
	}

	/**
	 * 应用get方法调用目标服务
	 * 
	 * @param headers
	 * @param request
	 * @param url
	 * @return
	 *
	 * @authz Fan.W
	 */
	private static String get(List<Header> headers, String url) {
		HttpClient client = new HttpClient();
		// 设置在http头的Content-Type的字符编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);// 设置连接超时时间一分钟
		client.getHttpConnectionManager().getParams().setSoTimeout(60000);// 设置读取响应时间一分钟
		GetMethod method = new GetMethod(url);
		String response = "";
		int rspCode;
		try {
			for (Header header : headers) {
				method.setRequestHeader(header);// 请求头参数
			}
			log.debug("GET: [" + url + "]");
			// 获取响应码 HttpStatus.SC_OK=200
			rspCode = client.executeMethod(method);
			if (HttpStatus.SC_OK != rspCode) {// 200响应成功
				log.debug("响应异常:响应状态码为: " + rspCode);
				// 抛出自定义异常: 调用目标服务失败,响应码为rspCode
			}
			// 获取响应信息
			response = method.getResponseBodyAsString();
			log.debug("RESPONSE: [" + url + "],响应信息: " + response);

			// ==============================打印请求头信息和响应头信息==============================================
			Header[] reqHeaders = method.getRequestHeaders();
			for (Header header : reqHeaders) {
				log.debug("请求信息 request  header [name]: " + header.getName() + " request header [value]: "
						+ header.getValue());
			}
			Header[] rspHeaders = method.getResponseHeaders();
			for (Header header : rspHeaders) {
				log.debug("响应信息 response header [name]: " + header.getName() + "response  header [value]: "
						+ header.getValue());
			}
			// ==============================打印请求头信息和响应头信息==============================================
		} catch (Exception e) {
			// 抛出自定义异常:发送http GET请求时出现异常
			log.debug("发送get请求出现异常", e);
		} finally {
			method.releaseConnection();// 关闭连接
		}
		return response;
	}

	/**
	 * 应用post方法调用目标服务
	 * 
	 * @param headers
	 * @param request
	 * @param url
	 * @return
	 *
	 * @authz Fan.W
	 */
	private static String post(List<Header> headers, String request, String url) {
		HttpClient client = new HttpClient();
		// 设置在http头的Content-Type的字符编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);// 设置连接超时时间一分钟
		client.getHttpConnectionManager().getParams().setSoTimeout(60000);// 设置读取响应时间一分钟
		PostMethod method = new PostMethod(url);
		String response = "";
		int rspCode;
		try {
			for (Header header : headers) {
				method.setRequestHeader(header);
			}
			log.debug("POST: [" + url + "],请求体: " + request);
			RequestEntity entity = new ByteArrayRequestEntity(request.getBytes("UTF-8"));
			method.setRequestEntity(entity);
			rspCode = client.executeMethod(method);
			if (HttpStatus.SC_OK != rspCode) {// 200响应成功
				log.debug("响应异常:响应状态码为: " + rspCode);
				// 抛出自定义异常: 调用目标服务失败,响应码为rspCode
			}
			response = method.getResponseBodyAsString();
			log.debug("RESPONSE: [" + url + "],响应信息: " + response);

			// ==============================打印请求头信息和响应头信息==============================================
			Header[] reqHeaders = method.getRequestHeaders();
			for (Header header : reqHeaders) {
				log.debug("请求信息 request  header [name]: " + header.getName() + " request header [value]: "
						+ header.getValue());
			}
			Header[] rspHeaders = method.getResponseHeaders();
			for (Header header : rspHeaders) {
				log.debug("响应信息 response header [name]: " + header.getName() + "response  header [value]: "
						+ header.getValue());
			}
			// ==============================打印请求头信息和响应头信息==============================================

		} catch (Exception e) {
			// 抛出自定义异常:发送http POST请求时出现异常
			log.debug("发送post请求出现异常", e);

		} finally {
			method.releaseConnection();// 关闭连接
		}
		return response;
	}

	@SuppressWarnings("deprecation")
	public static String post2(String url, String reqBody, Map<String, String> headMap) throws Exception {
		String remote_url = "http://localhost/ecasbe-api/v1/pay/quickPay.do";
		HttpClient httpclient = new HttpClient();

		PostMethod httppost = new PostMethod(remote_url);
		try {
			// httppost.setRequestHeader("Content-Type", "application/json");
			if (!headMap.isEmpty()) {
				for (Map.Entry<String, String> entry : headMap.entrySet()) {
					httppost.setRequestHeader(entry.getKey(), entry.getValue());
				}
			}

			httppost.setRequestBody(reqBody);
			int statusCode = httpclient.executeMethod(httppost);
			log.debug("响应状态码: " + statusCode);

			InputStream in = httppost.getResponseBodyAsStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			String result = out.toString();
			out.close();
			in.close();
			log.debug("响应: " + result);
			return result;
		} catch (Exception e) {
			log.error("通讯异常", e);
			throw e;
		} finally {
			httppost.releaseConnection();
		}
	}

	//@formatter:off
	/**
	 * 模拟表单提交
	 * @param url   请求地址
	 * @param file  文件参数
	 * @param headersMap  请求头信息
	 * @param reqParams   请求参数
	 * @return
	 * @throws Exception
	 */
	public static String postParamsAndFile(String url,File file,Map<String, String> headersMap,Map<String, String> reqParams) throws Exception {
	//@formatter:on
		HttpClient httpclient = new HttpClient();
		PostMethod httppost = new PostMethod(url);
		try {
			if (!headersMap.isEmpty()) {
				for (Map.Entry<String, String> entry : headersMap.entrySet()) {
					httppost.setRequestHeader(entry.getKey(), entry.getValue());
				}
			}

			FilePart fp = new FilePart("file", file);
			// 添加参数
			Part[] parts = new Part[reqParams.size() + 1];

			parts[0] = fp;

			StringPart sp = null;

			// String 类型参数
			int i = 1;
			if (!reqParams.isEmpty()) {
				for (Map.Entry<String, String> entry : reqParams.entrySet()) {
					sp = new StringPart(entry.getKey(), entry.getValue());
					parts[i] = sp;
					i++;
				}
			}

			MultipartRequestEntity mre = new MultipartRequestEntity(parts, httppost.getParams());
			httppost.setRequestEntity(mre);
			// 状态码
			int statusCode = httpclient.executeMethod(httppost);
			System.out.println(statusCode);

			InputStream in = httppost.getResponseBodyAsStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			String result = out.toString();
			out.close();
			in.close();
			log.debug("响应信息：" + result);
			return result;
		} catch (Exception e) {
			log.error("通讯异常", e);
			throw e;
		} finally {
			httppost.releaseConnection();
		}
	}

}
