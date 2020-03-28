package com.wf.seeker.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * springboot统一处理处理404、500等错误页面
 * 
 * @author Fan.W
 * @since 1.8
 */
@Controller
public class SeekerErrorController implements ErrorController {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}

	/**
	 * 可以自定义公共返回报文：https://blog.csdn.net/qianyiyiding/article/details/76691428
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		// 获取statusCode:401,404,500
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == 404) {
			return "/errorpage/404";
		} else {
			return "/errorpage/500";
		}
	}

}
