package com.wf.seeker.mytest_cases.testImageCompress;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * <dependency> <groupId>net.coobird</groupId> <artifactId>thumbnailator</artifactId>
 * <version>0.4.8</version></dependency>
 * 
 * @author Fan.W
 * @since 1.8
 */
// @Component
public class PicCompress implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(PicCompress.class);

	// public static void main(String[] args) throws Exception {
	// File file = new File("E://hello.png");
	// if (file.isFile()) {
	// String p = file.getParent() + "//320_320//" + file.getName();
	// File newFile = new File(p);
	// if (!newFile.exists()) {
	// newFile.getParentFile().mkdirs();
	// newFile.createNewFile();
	// Thumbnails.of(file).size(320, 320).toFile(newFile.getPath());
	// }
	// }
	// compress(new File("E://app"));
	// }

	//@formatter:off
	public static void compress(File file) throws IOException {
		if (file.isDirectory() && !file.getAbsolutePath().contains("160_160") && !file.getAbsolutePath().contains("320_320")) {
			logger.info("当前压缩图片目录：{}", file.getAbsolutePath());
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (File f : files) {
					compress(f);
				}
			}
		}
		if (file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".PNG") || file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".jpeg") || file.getName().endsWith(".JPEG"))) {

			File file_160 = new File(file.getParent() + "//160_160//" + file.getName());
			if (!file_160.exists()) {
				file_160.getParentFile().mkdirs();
				file_160.createNewFile();
				Thumbnails.of(file).size(160, 160).toFile(file_160.getPath());
				// logger.info("图片名称：{}，160*160压缩后目录：{}", file.getName(), file_160.getAbsolutePath());
			}

			File file_320 = new File(file.getParent() + "//320_320//" + file.getName());
			if (!file_320.exists()) {
				file_320.getParentFile().mkdirs();
				file_320.createNewFile();
				Thumbnails.of(file).size(320, 320).toFile(file_320.getPath());
				// logger.info("图片名称：{}，320*320压缩后目录：{}", file.getName(), file_320.getAbsolutePath());
			}
		}
	}
	//@formatter:on
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		// String path = PropertiesUtil.instance().getPath();
		String path = "";
		logger.info("===============压缩图片任务执行开始，压缩目录：{}========", path);
		// System.out.println(path);
		compress(new File(path));
		logger.info("=======压缩图片任务执行结束=====");
		System.exit(0);
	}
}
