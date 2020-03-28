package com.wf.seeker.mytest_cases.testIO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 复制文件测试
 * 
 * @author Fan.W
 * @since 1.8
 */
public class CopyFile {

	public static File touchFile(String path) {
		File newfile = new File(path);
		if (!newfile.exists()) {
			newfile.getParentFile().mkdirs();// 创建目录
			try {
				newfile.createNewFile();// 创建文件

			} catch (IOException e) {
			}
		}
		return newfile;
	}

	// 普通输入输出流单字节读取
	private static void method1(String srcFile, String destFile) throws Exception {
		touchFile(destFile);
		// 复制用了3174毫秒
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		int by = 0;
		while ((by = fis.read()) != -1) {
			fos.write(by);
		}
		fos.close();
		fis.close();
	}

	// 普通输入输出流字节数组读取
	private static void method2(String srcFile, String destFile) throws Exception {
		touchFile(destFile);

		// 复制用了13毫秒
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] bys = new byte[1024];
		int len = 0;
		while ((len = fis.read(bys)) != -1) {
			fos.write(bys, 0, len);
		}
		fos.close();
		fis.close();
	}

	// 高效输入输出流单字节读取
	private static void method3(String srcFile, String destFile) throws Exception {
		touchFile(destFile);

		// 复制用了167毫秒
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
		int by = 0;
		while ((by = bis.read()) != -1) {
			bos.write(by);
		}
		bos.close();
		bis.close();
	}

	// 高效输入输出流字节数组读取
	private static void method4(String srcFile, String destFile) throws Exception {
		touchFile(destFile);

		// 复制用了9毫秒

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
		byte[] bys = new byte[1024];
		int len = 0;
		while ((len = bis.read(bys)) != -1) {
			bos.write(bys, 0, len);
		}
		bos.close();
		bis.close();
	}

	/**
	 * 结论：不考虑字符集转换method4最快，考虑字符集转换method6最快
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// aaa.txt 1M 测试结果：
		// method1:普通输入输出流单字节读取,复制用了19561毫秒
		// method2:普通输入输出流字节数组读取,复制用了15毫秒
		// method3:高效输入输出流单字节读取,复制用了80毫秒
		// method4:高效输入输出流字节数组读取16毫秒
		// method5:字符缓冲流一次读写一个字符221毫秒
		// method6:字符缓冲流一次读写一个字符数组63毫秒
		// method7:字符缓冲流一次读写一个字符串227毫秒

		// Long start1 = System.currentTimeMillis();
		// method1("W:/aaa.txt", "W:/bbb1.txt");
		// Long end1 = System.currentTimeMillis();
		// System.out.println("method1:普通输入输出流单字节读取,复制用了" + (end1 - start1) + "毫秒");

		Long start2 = System.currentTimeMillis();
		method2("W:/aaa.txt", "W:/bbb2.txt");
		Long end2 = System.currentTimeMillis();
		System.out.println("method2:普通输入输出流字节数组读取,复制用了" + (end2 - start2) + "毫秒");

		// Long start3 = System.currentTimeMillis();
		// method3("W:/aaa.txt", "W:/bbb3.txt");
		// Long end3 = System.currentTimeMillis();
		// System.out.println("method3:高效输入输出流单字节读取,复制用了" + (end3 - start3) + "毫秒");

		Long start4 = System.currentTimeMillis();
		method4("W:/aaa.txt", "W:/bbb4.txt");
		Long end4 = System.currentTimeMillis();
		System.out.println("method4:高效输入输出流字节数组读取" + (end4 - start4) + "毫秒");

		// Long start5 = System.currentTimeMillis();
		// method5("W:/aaa.txt", "W:/bbb5.txt");
		// Long end5 = System.currentTimeMillis();
		// System.out.println("method5:字符缓冲流一次读写一个字符" + (end5 - start5) + "毫秒");

		Long start6 = System.currentTimeMillis();
		method6("W:/aaa.txt", "W:/bbb6.txt");
		Long end6 = System.currentTimeMillis();
		System.out.println("method6:字符缓冲流一次读写一个字符数组" + (end6 - start6) + "毫秒");

		Long start7 = System.currentTimeMillis();
		method7("W:/aaa.txt", "W:/bbb7.txt");
		Long end7 = System.currentTimeMillis();
		System.out.println("method7:字符缓冲流一次读写一个字符串" + (end7 - start7) + "毫秒");

	}

	// 字符缓冲流一次读写一个字符
	private static void method5(String srcString, String destString) throws IOException {
		touchFile(destString);

		BufferedReader br = new BufferedReader(new FileReader(srcString));
		BufferedWriter bw = new BufferedWriter(new FileWriter(destString));
		int ch = 0;
		while ((ch = br.read()) != -1) {
			bw.write(ch);
		}
		bw.close();
		br.close();
	}

	// 字符缓冲流一次读写一个字符数组
	private static void method6(String srcString, String destString) throws IOException {
		touchFile(destString);

		// BufferedReader br = new BufferedReader(new FileReader(srcString));
		// BufferedWriter bw = new BufferedWriter(new FileWriter(destString));

		// InputStreamReader 是字节流通向字符流的桥梁，使用指定的charset读取字节并将其解码为字符。为了达到最高效率，可要考虑在 BufferedReader 内包装 InputStreamReader
		// InputStreamReader br = new InputStreamReader(new BufferedInputStream(new FileInputStream(new
		// File(srcString))),
		// "GBK");
		// OutputStreamWriter bw = new OutputStreamWriter(
		// new BufferedOutputStream(new FileOutputStream(new File(destString))), "GBK");

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcString)), "GBK"));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(new File(destString)), "GBK"));
		char[] chs = new char[1024];
		int len = 0;
		while ((len = br.read(chs)) != -1) {
			bw.write(chs, 0, len);
		}
		bw.close();
		br.close();
	}

	// 字符缓冲流一次读写一个字符串
	private static void method7(String srcString, String destString) throws IOException {
		touchFile(destString);

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcString)), "GBK"));
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(new File(destString)), "GBK"));
		String line = null;
		while ((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
			bw.flush();
		}
		bw.close();
		br.close();
	}
}
