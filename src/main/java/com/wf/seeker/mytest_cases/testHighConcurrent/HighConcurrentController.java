package com.wf.seeker.mytest_cases.testHighConcurrent;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
@Controller
@RequestMapping("/seeker")
public class HighConcurrentController {

	@RequestMapping(value = "/highConcurrentTest")
	@ResponseBody
	public void highConcurrentTest() {

		CountDownLatch startSignal = new CountDownLatch(1);// 初始化计数器为 一

		for (int i = 0; i < 100; ++i) {
			new Thread(new Task(startSignal)).start();
		}

		startSignal.countDown();// 计数器減一 所有线程释放 同时跑
	}

	class Task implements Runnable {
		private final CountDownLatch startSignal;

		Task(CountDownLatch startSignal) {
			this.startSignal = startSignal;
		}

		public void run() {
			try {
				startSignal.await();// 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
				// #####################单笔测试内容############################
				BufferedReader br = new BufferedReader(new FileReader("e:/abc.txt"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append("\n");
				}
				post("", sb.toString());
				// #####################单笔测试内容############################
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static final String URL = "http://172.30.1.47:8083/ecapi/v1/account/acctRecharge.do";

	@SuppressWarnings("deprecation")
	public static void post(String url, String reqBody) {
		HttpClient httpclient = new HttpClient();
		PostMethod httppost = new PostMethod(URL);
		try {
			httppost.setRequestHeader("Content-Type", "application/json");
			httppost.setRequestBody(reqBody);
			int statusCode = httpclient.executeMethod(httppost);
			System.out.println("响应状态码: " + statusCode);

			InputStream in = httppost.getResponseBodyAsStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			String result = out.toString();

			int a = 0;

			if (!result.equals("success")) {
				a = a + 1;
			}
			out.close();
			in.close();
			System.out.println("发送充值请求,电子账户响应信息: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httppost.releaseConnection();
		}
	}

}
