package com.wf.seeker.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
public class MD5Utils {

	private static MessageDigest digest = null;

	public static final String hash(byte[] data) {
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
			if ((bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Integer.toString(bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * MD5(32位)
	 * 
	 * @param instr 要加密的字符串
	 * @return 返回加密后的字符串
	 */
	public final static String encoderByMd5With32Bit(String instr) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			if (instr != null && !"".equals(instr)) {
				byte[] strTemp = instr.getBytes();
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
				return new String(str).toUpperCase();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	// 92d7ddd2a010c59511dc2905b7e14f64
	public static void main(String[] args) {
		System.out.println(encoderByMd5With32Bit("1qaz@WSX").toLowerCase().equals("92d7ddd2a010c59511dc2905b7e14f64"));
	}
}
