package com.wf.seeker.mytest_cases.testSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title
 * @description
 * @since JDK1.8
 */
public class SocketUtils {
	/** 日志工具类 */
	private static Logger log = LoggerFactory.getLogger(SocketUtils.class);
	/** 字符集 */
	private static String ENCODING = "UTF-8";

	public static void setEncoding(String encoding) {
		SocketUtils.ENCODING = encoding;
	}

	/**
	 * 发送socket请求(客户端)
	 * 
	 * @param reqBody 请求体
	 * @param ip
	 * @param port
	 * @return
	 *
	 * @authz Fan.W
	 */
	public static String execute(String reqBody, String ip, String port) {
		log.debug("SOCKET 通讯 IP: " + ip + ",端口: " + port);
		// 初始化连接
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			socket = new Socket(ip, Integer.valueOf(port));
			// 设置超时时间45秒
			socket.setSoTimeout(45000);
			// 初始化输出流
			os = socket.getOutputStream();
			// socket定长报文的长度(通常是前八位)
			String lenStr = StringUtils.leftPad(String.valueOf(reqBody.getBytes(ENCODING).length), 8, "0");
			// 请求报文=长度+请求体
			String reqMsg = lenStr + reqBody;
			log.debug("socket 发送请求报文: " + reqMsg);

			// 发送请求
			os.write(reqMsg.getBytes(ENCODING));
			os.flush();
			socket.shutdownOutput();

			// 初始化输入流
			is = socket.getInputStream();
			// 获取响应报文头长度
			byte[] lenBytes = new byte[8];
			is.read(lenBytes);
			String rspLenth = new String(lenBytes);
			log.debug("响应报文长度为: " + rspLenth);

			// 判断响应报文头
			if (rspLenth.length() != 8) {
				log.error("响应报文头格式不正确 ");
			}

			// 读取响应报文体
			byte[] rspBody = new byte[Integer.valueOf(rspLenth)];
			is.read(rspBody);

			return new String(rspBody, ENCODING);

		} catch (Exception e) {
			// 抛出自定义异常
		} finally {
			if (os != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (is != null) {
				try {
					is.close();
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
		return null;
	}

	/**
	 * 服务端
	 * 
	 * @param port
	 *
	 * @authz Fan.W
	 */
	public static void server(String port) {
		try {
			ServerSocket ss = new ServerSocket(Integer.valueOf(port));
			while (true) {
				Socket socket = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

				String str = null;
				StringBuffer sb = new StringBuffer("");
				if ((str = br.readLine()) != null) {
					sb.append(str);
					sb.append("\n");
				}

				String reqMsg = sb.toString();
				// 去除最后一个换行
				String data = reqMsg.substring(0, reqMsg.length() - 1);
				log.debug("服务端接受数据 :　" + data);
				socket.shutdownInput();

				bw.write("0000success");

				bw.close();
				br.close();
				socket.close();
			}

		} catch (Exception e) {
			// 抛出自定义异常
		}
	}
}
