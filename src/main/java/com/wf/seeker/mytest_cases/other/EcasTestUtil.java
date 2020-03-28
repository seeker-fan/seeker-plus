package com.wf.seeker.mytest_cases.other;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 电子账户系统测试类
 * 
 * @title
 * @description
 * @since JDK1.8
 * 
 */
public class EcasTestUtil {

	private static MessageDigest digest = null;

	private static String KEY = "6C6B2CCC92854CBD9E607B6BDE7D4129";

	public static Logger logger = LoggerFactory.getLogger(EcasTestUtil.class);

	public static final String URL = "http://172.20.1.47:8080/ecps/handle.do";

	public static void main(String[] args) throws Exception {
		String request = new String(getReq(), "UTF-8");
		CloseableHttpClient client = HttpClients.createDefault();
		// 设置传输超时和连接超时
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
		HttpPost post = new HttpPost(URL);
		post.setConfig(config);
		post.addHeader("Content-Type", "application/json;charset=UTF-8");
		post.addHeader("X-Ecaspsplus-SignatureType", "MD5");
		post.addHeader("X-Ecaspsplus-Signature", getSign());
		ByteArrayEntity entity = new ByteArrayEntity(request.getBytes("UTF-8"));
		post.setEntity(entity);
		CloseableHttpResponse execute = client.execute(post);
		String rsp = EntityUtils.toString(execute.getEntity());
		logger.debug("电子账户响应信息:　" + rsp);
	}

	/**
	 * 获取请求报文
	 * 
	 * @return
	 * @throws Exception
	 */
	public static byte[] getReq() throws Exception {
		byte[] bodyByte = FileUtils.readFileToByteArray(new File("e:/123.txt"));
		return bodyByte;
	}

	/**
	 * 获取md5摘要
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getSign() throws Exception {
		byte[] bodyByte = getReq();
		byte[] accKeyByte = KEY.getBytes();
		byte[] data = new byte[bodyByte.length + accKeyByte.length];
		System.arraycopy(bodyByte, 0, data, 0, bodyByte.length);
		System.arraycopy(accKeyByte, 0, data, bodyByte.length, accKeyByte.length);
		System.err.println(hash(data));
		return hash(data);
	}

	public synchronized static final String hash(byte[] data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println(
						"Failed to load the MD5 MessageDigest. " + "Jive will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		digest.update(data);
		return encodeHex(digest.digest());
	}

	private static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;
		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Integer.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
}
