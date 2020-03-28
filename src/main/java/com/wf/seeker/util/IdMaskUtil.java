package com.wf.seeker.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 身份证掩码
 * 
 * @author Fan.W
 * @since 1.8
 */
public class IdMaskUtil {
	public static void main(String[] args) {
		System.out.println(idmask("140511199311081273"));
		System.out.println(nameMask("卫帆"));
		System.out.println(nameMask("冯小刚"));
		System.out.println(nameMask("捷克均已"));
	}

	public static String idmask(String idCardNum) {
		return idMask(idCardNum, 6, 4);
	}

	/**
	 * @param idCardNum 身份证号
	 * @param front要展示前几位
	 * @param end要展示后几位
	 * @return
	 */
	public static String idMask(String idCardNum, int front, int end) {
		// 身份证不能为空
		if (StringUtils.isEmpty(idCardNum)) {
			return "";
		}
		// 需要截取的长度不能大于身份证号长度
		if ((front + end) > idCardNum.length()) {
			return null;
		}
		// 需要截取的不能小于0
		if (front < 0 || end < 0) {
			return null;
		}
		// 计算*的数量
		int asteriskCount = idCardNum.length() - (front + end);
		StringBuffer asteriskStr = new StringBuffer();
		for (int i = 0; i < asteriskCount; i++) {
			asteriskStr.append("*");
		}
		String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
		return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
	}

	public static String nameMask(String name) {
		if (StringUtils.isEmpty(name))
			return "";
		if (name.length() <= 1)
			return name + "*";
		return name.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(name.length() - 1));
	}

	private static String createAsterisk(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append("*");
		}
		return sb.toString();
	}
}
