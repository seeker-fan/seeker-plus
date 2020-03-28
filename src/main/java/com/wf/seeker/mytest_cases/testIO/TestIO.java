package com.wf.seeker.mytest_cases.testIO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
public class TestIO {

	/**
	 * 将文件读成字节
	 * 
	 * @param path
	 * @return
	 */
	public static byte[] file2byte(String path) {
		try {
			FileInputStream in = new FileInputStream(new File(path));
			// 当文件没有结束时，每次读取一个字节显示
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字节写入文件
	 * 
	 * @param path
	 * @param data
	 */
	public static void byte2file(String path, String fileName, byte[] data) {
		File file = touchFile(path, fileName);
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(data);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照指定字符集读取文件内容转换为list
	 * 
	 * @param path
	 * @param encoder
	 * @return
	 */
	public static ArrayList<String> file2list(String path, String encoder) {
		ArrayList<String> alline = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoder));
			String str = new String();
			while ((str = in.readLine()) != null) {
				alline.add(str);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alline;
	}

	/**
	 * 创建空文件
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File touchFile(String path, String fileName) {
		File newfile = new File(path, fileName);
		if (!newfile.exists()) {
			newfile.getParentFile().mkdirs();// 创建目录
			try {
				newfile.createNewFile();// 创建文件

			} catch (IOException e) {
			}
		}
		return newfile;
	}

	/**
	 * 按照指定字符集，将list写入文件
	 * 
	 * @param path
	 * @param data
	 * @param encoder
	 */
	public static void list2file(String path, String fileName, ArrayList<String> data, String encoder) {
		File file = touchFile(path, fileName);
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoder));
			for (String str : data) {
				out.write(str);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String system2str() throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		return stdin.readLine();

		// Scanner s = new Scanner(System.in);
		// System.out.println("请输入字符串：");
		// while (true) {
		// String lin = s.nextLine();
		// if (lin.equals("exit"))
		// break;
		// System.out.println(">>>" + lin);
		// }
	}

	public static String file2str(String path, String encoder) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoder));
			String str = new String();
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void str2file(String path, String data, String encoder) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoder));
			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Double> file2matrix(String path) {
		ArrayList<Double> alldata = new ArrayList<Double>();
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
			// 利用DataInputStream来读数据
			while (true) {
				alldata.add(in.readDouble());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alldata;
	}

	// 测试程序
	public static void main(String[] args) throws Exception {
		// byte[] data = file2byte("W:/aaa.txt"); // 读取文件
		// byte2file("W:/", "bbb.txt", data); // 写入文件

		// ArrayList<String> allline = file2list("W:/aaa.txt", "GBK"); // 按行读取文件
		// list2file("W:/", "bbb.txt", allline, "UTF-8"); // 存储文件
		// list2file("W:/", "ccc.txt", allline, "GBK"); // 存储文件

		// String content = file2str("W:/aaa.txt", "UTF-8"); // 读取文件
		// str2file("test3.txt", content, "UTF-8"); // 存储文件

		// String message = system2str();
		// System.out.println("控制台输入：" + message);

		Scanner s = new Scanner(System.in);
		System.out.println("请输入字符串：");
		while (true) {
			String lin = s.nextLine();
			if (lin.equals("exit"))
				break;
			System.out.println(">>>" + lin);
		}

		// FileWriter wr = new FileWriter("C:\\Users\\admin\\Desktop\\test.csv", true);
		// BufferedWriter out = new BufferedWriter(wr);
		// out.write(vars.get("id") + "," + vars.get("name"));
		// out.close();
		// wr.close();

	}
}
