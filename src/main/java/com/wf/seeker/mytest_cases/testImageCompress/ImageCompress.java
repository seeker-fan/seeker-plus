package com.wf.seeker.mytest_cases.testImageCompress;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

//@formatter:off
/**
		   <dependency>
 *             <groupId>net.coobird</groupId>
 *             <artifactId>thumbnailator</artifactId>
 *             <version>0.4.8</version>
 *         </dependency>
 * @author Fan.W
 * @since 1.8
 */
public class ImageCompress {
	public static void main(String[] args) throws IOException {
		run(new File("D://Program Files/feiq/Recv Files/2019/2019"), 1, false);
	}

	public static void run(File file, int level, boolean status) throws IOException {
		String filename = file.getName();
		if (status) {
			if (filename.endsWith(".png") || filename.endsWith(".PNG") || filename.endsWith(".jpg")
					|| filename.endsWith(".JPG") || filename.endsWith(".jpeg") || filename.endsWith(".JPEG")) {
				System.out.println(filename);
				Thumbnails.of(file).size(160, 160).toFile(file.getPath());
			}
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (File f : files) {
					if (file.getName().equals("160-160")) {
						file.renameTo(new File(file.getPath().replace("160-160", "160_160")));
						System.out.println(file.getPath() + "------->" + file.getPath().replace("160-160", "160_160"));
					}
					if (file.getName().equals("160_160")) {
						run(f, level + 1, true);
					} else {
						run(f, level + 1, false);
					}

				}
			}
		}
	}
}
