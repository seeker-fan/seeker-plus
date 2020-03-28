package com.wf.seeker.mytest_cases.testSftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wf.seeker.configure.PropertiesUtil;
import com.wf.seeker.util.SftpUtil;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
@Controller
public class TestSftp {

	/**
	 * 直接上传
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/toupload" })
	public String toupload() {
		return "upload";
	}

	/**
	 * 点击按钮触发上传
	 * 
	 * @return
	 */
	@RequestMapping("/toupload1")
	public String toupload1() {
		return "upload1";
	}

	/**
	 * 文件上传sftp:/usr/local/sftp/seeker/upload/xxxx.xx
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/upload" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, String> upload(@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		// String tempPath = PropertiesUtil.instance().getSftp().getLinuxTempPath();//linux路径
		String tempPath = PropertiesUtil.instance().getSftp().getWindowsTempPath();// windows路径

		File newfile = new File(tempPath, file.getOriginalFilename());
		if (!newfile.exists()) {
			newfile.getParentFile().mkdirs();// 创建目录
			try {
				newfile.createNewFile();// 创建文件
			} catch (IOException e) {
			}
		}

		// 上传到本地目录
		// FileOutputStream out = new FileOutputStream(new File("W:/", file.getOriginalFilename()));
		// out.write(file.getBytes());
		// out.close();

		FileUtils.copyInputStreamToFile(file.getInputStream(), newfile);

		FileInputStream in = new FileInputStream((File) newfile);

		String uploadPath = PropertiesUtil.instance().getSftp().getUploadPath();

		String path = uploadPath + file.getOriginalFilename();
		SftpUtil sftp = SftpUtil.getSingleTon();
		sftp.connect();
		sftp.putFile(in, path);
		sftp.disContent();
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("flag", PropertiesUtil.instance().getSftp().getPrifixUrl() + file.getOriginalFilename());
		return returnMap;
	}

	/**
	 * 文件下载：http://localhost:5555/download?fileName=xxxxx.xx
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/download" }, method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, String> download(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileName = request.getParameter("fileName");

		// String tempPath = PropertiesUtil.instance().getSftp().getLinuxTempPath();//linux路径
		String tempPath = PropertiesUtil.instance().getSftp().getWindowsTempPath();// windows路径
		String uploadPath = PropertiesUtil.instance().getSftp().getUploadPath();// /seeker/upload

		SftpUtil sftp = SftpUtil.getSingleTon();
		sftp.connect();
		String orignName = sftp.getFiles(uploadPath, fileName, tempPath);
		if (!StringUtils.isEmpty(orignName)) {
			com.wf.seeker.util.FileUtils.download(response, tempPath + orignName);
		}
		sftp.disContent();
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("flag", "success");
		return returnMap;
	}

}
