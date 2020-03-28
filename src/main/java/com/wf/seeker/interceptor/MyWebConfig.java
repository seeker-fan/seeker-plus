package com.wf.seeker.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Fan.W
 * @since 1.8
 */
@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter {
	/**
	 * 注册 拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new ErrorInterceptor());
	}
}
