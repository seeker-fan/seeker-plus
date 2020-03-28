package com.wf.seeker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.wf.seeker.configure.PropertiesUtil;

public class SftpUtil {

	private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

	private static SftpUtil sftpUtil;
	private static ChannelSftp sftp = null;
	private static Session session = null;
	private static Channel channel = null;
	private static String host = PropertiesUtil.instance().getSftp().getHost();
	private static String port = PropertiesUtil.instance().getSftp().getPort();
	private static String username = PropertiesUtil.instance().getSftp().getUsername();
	private static String password = PropertiesUtil.instance().getSftp().getPassword();
	private static final int timeout = 6000;

	private SftpUtil() {
	}

	public static SftpUtil getSingleTon() {
		if (sftpUtil == null) {
			// System.out.println("hello");
			synchronized (SftpUtil.class) {
				if (sftpUtil == null)
					sftpUtil = new SftpUtil();
			}
		}
		return sftpUtil;
	}

	public static void main(String[] args) throws Exception {
		SftpUtil sftp = SftpUtil.getSingleTon();
		sftp.connect();
		FileInputStream fi = new FileInputStream(new File("W:\\140878254492300257.jpg"));
		sftp.putFile(fi, "W:/");
		sftp.disContent();
	}

	public void putFile(InputStream in, String dstPath) throws Exception {
		sftp.put(in, dstPath, ChannelSftp.OVERWRITE);
		logger.debug("文件上传成功,上传目录为：" + dstPath);
	}

	/**
	 * @param path 文件存放路径
	 * @param file 文件名称
	 * @param targetPath 存放本地目录
	 * @return
	 * @throws SftpException
	 */
	@SuppressWarnings("unchecked")
	public String getFiles(String path, String file, String targetPath) throws SftpException {
		Vector<LsEntry> list = sftp.ls(path);
		sftp.cd(path);
		Iterator<LsEntry> sftpFileNames = list.iterator();
		while (sftpFileNames.hasNext()) {
			LsEntry isEntity = sftpFileNames.next();
			String fileName = isEntity.getFilename();
			if (fileName.contains(file)) {
				sftp.get(isEntity.getFilename(), targetPath);
				return isEntity.getFilename();
			}
		}
		return null;
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws JSchException
	 */
	public void connect() throws JSchException {

		JSch jsch = new JSch(); // 创建JSch对象
		session = jsch.getSession(username, host, Integer.parseInt(port)); // 根据用户名，主机ip，端口获取一个Session对象
		logger.debug("Session created.");
		if (password != null) {
			session.setPassword(password); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config); // 为Session对象设置properties
		session.setTimeout(timeout); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		logger.debug("Session connected.");

		logger.debug("Opening Channel.");
		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		logger.debug("Connected successfully to ftpHost = " + host + ",as ftpUserName = " + username + ", returning: "
				+ channel);
		sftp = (ChannelSftp) channel;
	}

	/**
	 * 销毁
	 * 
	 * @param sftp
	 */
	public void disContent() {
		if (sftp != null)
			sftp.disconnect();
		if (channel != null)
			channel.disconnect();
		if (session != null)
			session.disconnect();
	}

	/**
	 * 将SFTP SERVER上的文件下载到本地
	 * 
	 * @param destDir SFTP SERVER 上的目录
	 * @param destFileName 要下载的文件名称
	 * @param saveFile 保存到本地的文件路径
	 * @return
	 * @throws FileNotFoundException
	 * @throws SftpException
	 *
	 */
	public boolean download(String destDir, String destFileName, String saveFile)
			throws FileNotFoundException, SftpException {
		sftp.cd(destDir);
		File file = new File(saveFile);
		sftp.get(destFileName, new FileOutputStream(file));
		return true;
	}

	/**
	 * 判断目录下是否存在文件
	 * 
	 * @param directory 目标目录
	 * @param fileName 目标文件名
	 * @return 存在：true 不存在：false
	 * @throws SftpException
	 *
	 */
	@SuppressWarnings("rawtypes")
	public Boolean existFile(String filePath) throws Exception {
		Boolean flag = false;
		try {
			// sftp.cd("/");
			Vector ls = sftp.ls(filePath);
			if (ls != null) {
				logger.debug("File search success ! Directory for:[ " + filePath + " ] ");
				flag = true;
			}
		} catch (Exception e) {
			logger.debug("File not found [ " + filePath + " ] ");
		}
		return flag;
	}

	public String getHost() {
		return host;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the port
	 */
	public static String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public static void setPort(String port) {
		SftpUtil.port = port;
	}

}
