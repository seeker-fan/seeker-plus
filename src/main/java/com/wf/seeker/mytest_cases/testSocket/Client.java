package com.wf.seeker.mytest_cases.testSocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title
 * @description
 * @since JDK1.8
 */
public class Client {
	private static String ENCODING = "UTF-8";
	private static String IP = "127.0.0.1";
	private static int PORT = 1111;

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) throws Exception {
		execute();
		Runnable run = new Runnable() {
			public void run() {
				try {
					execute();
				} catch (Exception e) {
					// 添加日志记录该异常
					e.printStackTrace();
				}
			}
		};

		for (int i = 0; i < 10; i++) {
			List<Thread> list = new ArrayList<>();
			for (int j = 0; j < 500; j++) {
				list.add(new Thread(run));
			}
			for (Thread thread : list) {
				thread.start();
			}
		}
	}

	public static String execute() throws Exception {
		// 初始化连接变量
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;

		try {

			socket = new Socket();
			socket.connect(new InetSocketAddress(IP, PORT));
			/// ** 设置超时时间为 */
			socket.setSoTimeout(45000);

			outputStream = socket.getOutputStream();// 初始化输出流

			String reqBody = "卫帆你好";

			// 发送请求
			outputStream.write(reqBody.getBytes(ENCODING));
			outputStream.flush();

			//@formatter:off
			/**
			 * 1.在客户端或者服务端通过socket.shutdownOutput()都是单向关闭的，即关闭客户端的输出流并不会关闭服务端的输出流，所以是一种单方向的关闭流；
			 * 2.通过socket.shutdownOutput()关闭输出流，但socket仍然是连接状态，连接并未关闭
			 * 3.如果直接关闭输入或者输出流，即：in.close()或者out.close()，会直接关闭socket
			 */
			//@formatter:on
			socket.shutdownOutput();

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING));

			String str = null;
			StringBuffer sb = new StringBuffer("");
			while ((str = br.readLine()) != null) {

				sb.append(str);
				sb.append("\n");
			}
			String string = sb.toString();
			String data = string.substring(0, string.length() - 1);
			logger.debug("接受到服务端信息：" + data);

			return data;

		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}
		}

	}

}
