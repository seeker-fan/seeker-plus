package com.wf.seeker.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.wf.seeker.common.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * 
 */
@RestControllerAdvice
@Slf4j
public class SeekerExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(BusinessException.class)
	public Result<?> handleRRException(BusinessException e) {
		log.error(e.getMessage(), e);
		return Result.error(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result<?> handlerNoFoundException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result<?> handleDuplicateKeyException(DuplicateKeyException e) {
		log.error(e.getMessage(), e);
		return Result.error("数据库中已存在该记录");
	}

	@ExceptionHandler(Exception.class)
	public Result<?> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error("操作失败，" + e.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<?> HttpRequestMethodNotSupportedException(Exception e) {
		log.error(e.getMessage(), e);
		return Result.error("没有权限，请联系管理员授权");
	}

	/**
	 * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Result<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		log.error(e.getMessage(), e);
		return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public Result<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error(e.getMessage(), e);
		return Result.error("字段太长,超出数据库字段的长度");
	}

	@ExceptionHandler(PoolException.class)
	public Result<?> handlePoolException(PoolException e) {
		log.error(e.getMessage(), e);
		return Result.error("Redis 连接异常!");
	}

}
