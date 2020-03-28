package com.wf.seeker.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @title json转换处理工具
 * @description 为隔离框架对json工具依赖，在此处进行统一处理
 */
public class JsonUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 将json串转换为指定类型数据对象
	 * 
	 * @param json json串
	 * @param type 指定的数据类型
	 * @return 转换结果
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T jsonToObj(String json, Class<T> type) {
		try {
			return mapper.readValue(json, type);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("json转对象异常" + json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("json转对象异常" + json);
		}
	}

	/**
	 * 转换数据对象为json串
	 * 
	 * @param obj 数据对象
	 * @return 转换结果
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String objToJson(Object obj) {
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("对象格式化json异常" + obj);
		}
		return jsonStr;
	}

	// 调用方式： List<TaskRule> list = jsonToList(i.getIntegralDetails(), TaskRule.class);

	@SuppressWarnings("rawtypes")
	public static List jsonToList(String jsonStr, Class<?> elementClasses) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, elementClasses);
		try {
			return mapper.readValue(jsonStr, javaType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("json转list异常", e);
		}
	}

}
