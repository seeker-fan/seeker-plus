package com.wf.seeker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication
// @ImportResource("classpath:applicationContext-rabbitmq.xml")
@EnableScheduling
public class SeekerPlusApplication {

	public static void main(String[] args) {
		// System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(SeekerPlusApplication.class, args);
	}

	/**
	 * 使用@controller注解，但是想返回格式化后的json数据，则需要使用此@bean，或者说你可以直接使用@restcontroller注解
	 * 
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.需要定义一个convert转换消息的对象;
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		// 2:添加fastJson的配置信息;
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 3处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		// 4.在convert中添加配置信息.
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
		return new HttpMessageConverters(converter);
	}

}
