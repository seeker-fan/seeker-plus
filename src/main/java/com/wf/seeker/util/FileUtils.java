package com.wf.seeker.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 文件下载--服务端
	 * 
	 * @param response
	 * @param filePath
	 * @throws Exception
	 */
	public static void download(HttpServletResponse response, String filePath) throws Exception {
		// 文件名称
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
		Path path = Paths.get(filePath);
		byte[] content = Files.readAllBytes(path);
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		try {
			OutputStream out = response.getOutputStream();
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			// 添加日志记录该异常
			logger.debug("正常关闭");
		}
	}

	/**
	 * 文件下载--客户端
	 * 
	 * @param url
	 * @param headers
	 * @param reqBody
	 * @param targetFile 本地文件存储
	 */
	public static void fileDownload(String url, Map<String, String> headers, String reqBody, String targetFile) {
		File file = new File(targetFile);
		// 文件不存在
		if (!file.exists()) {
			file.getParentFile().mkdirs();// 创建目录
			try {
				file.createNewFile();// 创建文件
			} catch (IOException e) {
				logger.error("文件路径异常", e);
			}
		}

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		try {
			// httppost.setHeader("Content-Type", "application/json");
			if (!headers.isEmpty()) {// 请求头信息
				for (Entry<String, String> entry : headers.entrySet()) {
					httppost.addHeader(entry.getKey(), entry.getValue());
				}
			}

			HttpEntity entity = new ByteArrayEntity(reqBody.getBytes());
			httppost.setEntity(entity);

			CloseableHttpResponse execute = httpclient.execute(httppost);
			InputStream in = execute.getEntity().getContent();
			FileOutputStream out = new FileOutputStream(targetFile);
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			out.close();
			in.close();

			// import org.apache.commons.io.FileUtils;
			// FileUtils.copyInputStreamToFile(in, new File(targetFile));
		} catch (Exception e) {
			logger.error("下载文件发生异常：", e);
		} finally {
			httppost.releaseConnection();
		}
	}

	/**
	 * eccf文件下载客户端
	 * 
	 * @param theURL
	 * @param filePath
	 * @param chid
	 * @param echDate
	 * @param cfType
	 * @throws IOException
	 */
	public static void downloadFile(URL theURL, String filePath, String chid, String echDate, String cfType)
			throws IOException {
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			// 文件路径不存在时，自动创建目录
			dirFile.mkdir();
		}
		URLConnection connection = theURL.openConnection();
		HttpURLConnection httpUrlConnection = (HttpURLConnection) connection;
		httpUrlConnection.setRequestProperty("Charset", "UTF-8");
		httpUrlConnection.setRequestProperty("Content-Type", "application/json");
		httpUrlConnection.setRequestProperty("X-Ecascfplu-CfSign", "8D3107D1D8B9C3828EB857AA3FA3E0E8");
		httpUrlConnection.setRequestProperty("X-Ecascfplus-ReqTs", "20170727102548");

		// 头信息
		// Map<String, List<String>> map = connection.getHeaderFields();

		int code = httpUrlConnection.getResponseCode();// 状态码

		if (200 != code) {
			// response返回异常信息,提示用户
			logger.info("异常信息如下:");
			InputStream in = httpUrlConnection.getErrorStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[4 * 1024];
			int read;
			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}
			String result = out.toString();
			out.close();
			in.close();
			logger.info(result);
		} else {
			InputStream in = httpUrlConnection.getInputStream();
			FileOutputStream out = new FileOutputStream(filePath + "\\" + chid + echDate + cfType + ".txt");
			byte[] buffer = new byte[4 * 1024];
			int read;

			while ((read = in.read(buffer)) > 0) {
				out.write(buffer, 0, read);
			}

			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("outputStream close exception", e);
			}

			try {
				in.close();
			} catch (IOException e) {
				logger.info("inputStream");
				e.printStackTrace();
			}
			logger.info("下载成功");
		}
	}

	// public static void main(String[] args) throws Exception {
	// HttpClient httpClient = new HttpClient();
	// GetMethod get = new GetMethod(
	// "http://localhost:8082/eccf/v1/cf?chid=1217071317171753168&echDate=20171001&cfType=6");
	// get.addRequestHeader("Charset", "UTF-8");
	// get.addRequestHeader("Content-Type", "application/json");
	// get.addRequestHeader("X-Ecascfplu-CfSign", "B4D8DF7A2B30B9C3404A0E92C666B9B9");
	// get.addRequestHeader("X-Ecascfplus-ReqTs", "20170728145131");
	// int httpCode = httpClient.executeMethod(get);
	// logger.info("响应码:" + httpCode);
	// InputStream in = get.getResponseBodyAsStream();
	// FileUtils.copyInputStreamToFile(in, new File("d://xx123xx.txt"));
	// }

	public static void main(String[] args) throws Exception {
		// 下面添加服务器的IP地址和端口，以及要下载的文件路径
		String urlPath = "http://localhost:8081/eccf/v1/cf?chid=1217071317171753168&echDate=20170726&cfType=7";
		// String urlPath =
		// "https://testecasqz.srbank.cn/eccf/v1/cf?chid=1217052218160349781&echDate=20170524&cfType=1";
		// String urlPath =
		// "http://172.20.1.47:8080/eccf/v1/downloadBatch?chid=1217061914342923974&batchNo=5CFEA118EB47491AA31951B9170DA4F9&fileType=1";
		String chid = "1217071317171753168";
		String echDate = "20170726";
		String cfType = "7";
		// 下面代码是下载到本地的位置
		// String filePath = "d:/batch";
		// URL url = new URL(urlPath);
		// try {
		// downloadFile(url, filePath, chid, echDate, cfType);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		copy("W:/AAA", "W:/BBB");
	}
	// +++++++++++++++++++++模拟客户端下载++++++++++++++++++

	/**
	 * 文件转储
	 * 
	 * @param path
	 * @param copyPath
	 * @throws IOException
	 */
	public static void copy(String path, String copyPath) throws IOException {
		File filePath = new File(path);
		DataInputStream read;
		DataOutputStream write;
		if (filePath.isDirectory()) {
			File[] list = filePath.listFiles();
			for (int i = 0; i < list.length; i++) {
				String newPath = path + File.separator + list[i].getName();
				String newCopyPath = copyPath + File.separator + list[i].getName();
				File newFile = new File(copyPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				copy(newPath, newCopyPath);
			}
		} else if (filePath.isFile()) {
			read = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
			write = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(copyPath)));
			byte[] buf = new byte[1024 * 512];
			while (read.read(buf) != -1) {
				write.write(buf);
			}
			read.close();
			write.close();
		} else {
			System.out.println("请输入正确的文件名或路径名");
		}
	}
}
