package com.wf.seeker.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wf.seeker.entity.SysQuartzJob;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
@Mapper
public interface SysQuartzJobMapper {

	/**
	 * 查询单个定时任务
	 * 
	 * @param id
	 * @return
	 */
	@Select(" select id,job_class_name,cron_expression,parameter,description,status from sys_quartz_job where id = #{id}")
	SysQuartzJob findConfigById(@Param("id") String id);

	/**
	 * 查询所有定时任务（启用的 未删除的）
	 * 
	 * @return
	 */
	@Select(" select id,job_class_name,cron_expression,parameter,description,status from sys_quartz_job where status = 0 and del_flag = 0 ")
	List<SysQuartzJob> findAllSchedule();

}
