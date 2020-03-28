package com.wf.seeker.mytest_cases.testIO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
@Controller
@Slf4j
public class TestDownloadFileController {
	@PostMapping("/download")
	public void download(HttpServletResponse response) throws Exception {
		String filePath = "D:\\clues_data.csv";
		// 文件名称
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
		Path path = Paths.get(filePath);
		byte[] content = Files.readAllBytes(path);
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		try (OutputStream out = response.getOutputStream();) {
			out.write(content);
			out.flush();
		} catch (IOException e) {
			// 添加日志记录该异常
			log.debug("正常关闭");
		}
	}

	/**
	 * post请求下载文件
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		fileDownload("http://localhost:5556/download", new HashMap<String, String>(), "", "D:/AAA/BBB/aaa.csv");
	}

	public static void fileDownload(String url, Map<String, String> headers, String reqBody, String targetFile) {
		File file = new File(targetFile);
		// 文件不存在
		if (!file.exists()) {
			file.getParentFile().mkdirs();// 创建目录
			try {
				file.createNewFile();// 创建文件
			} catch (IOException e) {
				log.error("文件路径异常", e);
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
			log.error("下载文件发生异常：", e);
		} finally {
			httppost.releaseConnection();
		}
	}

}
