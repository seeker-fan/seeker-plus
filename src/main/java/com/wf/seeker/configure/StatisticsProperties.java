package com.wf.seeker.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @title
 * @description
 * @since Java8
 */
@Configuration
@ConfigurationProperties(prefix = "seeker", ignoreUnknownFields = true)
@Data
public class StatisticsProperties {
	private final Sftp sftp = new Sftp();

	@Data
	public static class Sftp {
		private String port;
		private String username;
		private String password;
		private String host;
		private String uploadPath;
		private String linuxTempPath;
		private String windowsTempPath;
		private String prifixUrl;
	}
}
