package com.wf.seeker.mytest_cases.testEasyExcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/****
 * 
 * @author Fan.W
 * @since 1.7
 */
public class PersonModel extends BaseRowModel {

	@ExcelProperty(value = "姓名", index = 0)
	private String name;

	@ExcelProperty(value = "年龄", index = 1)
	private String age;

	@ExcelProperty(value = "邮箱", index = 2)
	private String email;

	@ExcelProperty(value = "地址", index = 3)
	private String address;

	@ExcelProperty(value = "性别", index = 4)
	private String sax;

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
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the sax
	 */
	public String getSax() {
		return sax;
	}

	/**
	 * @param sax the sax to set
	 */
	public void setSax(String sax) {
		this.sax = sax;
	}

}
