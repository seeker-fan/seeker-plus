package com.wf.seeker.mytest_cases.testSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端
 * 
 * @author Fan.W
 * @since 1.8
 */
public class Server {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	/**
	 * 监听的端口
	 */
	public static final int PORT = 1111;

	public static void main(String[] args) {
		logger.debug("socket服务端启动了!!!");
		Server server = new Server();
		server.init();
	}

	@SuppressWarnings("resource")
	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket client = serverSocket.accept();

				new Task(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Task implements Runnable {
		private Socket socket;

		public Task(Socket socket) {
			this.socket = socket;
			new Thread(this).start();
		}

		@Override
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

				String str = null;
				StringBuffer sb = new StringBuffer("");
				while ((str = br.readLine()) != null) {

					sb.append(str);
					sb.append("\n");
				}
				String string = sb.toString();
				String data = string.substring(0, string.length() - 1);
				logger.debug("服务端接收：" + data);

				//@formatter:off
				/**
				 * 1.在客户端或者服务端通过socket.shutdownOutput()都是单向关闭的，即关闭客户端的输出流并不会关闭服务端的输出流，所以是一种单方向的关闭流；
				 * 2.通过socket.shutdownOutput()关闭输出流，但socket仍然是连接状态，连接并未关闭
				 * 3.如果直接关闭输入或者输出流，即：in.close()或者out.close()，会直接关闭socket
				 */
				//@formatter:on
				socket.shutdownInput();
				bw.write("0000000success");
				logger.debug("服务端发送：0000000success");

				bw.close();
				br.close();
				socket.close();

			} catch (IOException e) {
				logger.error("服务器异常：", e);
			}
		}
	}
}
