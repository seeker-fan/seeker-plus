package com.wf.seeker.mytest_cases.testEasyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/****
 * 
 * @author Fan.W
 * @since 1.7
 */
public class ProductModel extends BaseRowModel {
	@ExcelProperty(value = "产品id", index = 1)
	private String id;

	@ExcelProperty(value = "产品名称", index = 0)
	private String name;

	@ExcelProperty(value = "产品数量", index = 2)
	private String count;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}

}
