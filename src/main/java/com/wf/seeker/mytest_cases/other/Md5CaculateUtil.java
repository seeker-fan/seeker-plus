package com.wf.seeker.mytest_cases.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5CaculateUtil {

	private Md5CaculateUtil() {

	}

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static void main(String[] args) throws Exception, Exception {
		// System.out.println(MD5Utils.encoderByMd5With32Bit(FileUtils.readFileToString(new File("C:/back.dmp"))));
		long start = System.currentTimeMillis();
		System.out.println("开始计算文件MD5值,请稍后...");
		String fileName = "C:/Users/weifan/Desktop/um_api.properties";
		String hashType = "MD5";
		String hash = getHash(fileName, hashType);
		System.out.println("MD5:" + hash);
		long end = System.currentTimeMillis();
		System.out.println("一共耗时:" + (end - start) + "毫秒");

		// 对文件md5和对文件内容进行md5不一样！！！！！！！！！！！！！！！！！！！！
		DigestUtils.md5Hex(new FileInputStream(new File("C:/Users/weifan/Desktop/um_api.properties")));
	}

	public static String getHash(String fileName, String hashType) throws IOException, NoSuchAlgorithmException {

		File f = new File(fileName);
		System.out.println(" -------------------------------------------------------------------------------");
		System.out.println("|当前文件名称:" + f.getName());
		System.out.println("|当前文件大小:" + (f.length() / 1024 / 1024) + "MB");
		System.out.println("|当前文件路径[绝对]:" + f.getAbsolutePath());
		System.out.println("|当前文件路径[---]:" + f.getCanonicalPath());
		System.out.println(" -------------------------------------------------------------------------------");

		InputStream ins = new FileInputStream(f);

		byte[] buffer = new byte[8192];
		MessageDigest md5 = MessageDigest.getInstance(hashType);

		int len;
		while ((len = ins.read(buffer)) != -1) {
			md5.update(buffer, 0, len);
		}

		ins.close();
		byte[] dt = md5.digest();
		// 两种方式的MD5值是一样的
		System.out.println("aaaaa   " + DigestUtils.md5Hex(dt));
		System.out.println("bbbbb   " + encoderByMd5With32Bit(dt));
		// 也可以用apache自带的计算MD5方法
		return DigestUtils.md5Hex(dt);
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			char c0 = hexDigits[(bytes[l] & 0xf0) >> 4];
			char c1 = hexDigits[bytes[l] & 0xf];
			stringbuffer.append(c0);
			stringbuffer.append(c1);
		}
		return stringbuffer.toString();
	}

	public final static String encoderByMd5With32Bit(byte[] strTemp) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			if (strTemp != null) {
				// MD5计算方法
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
