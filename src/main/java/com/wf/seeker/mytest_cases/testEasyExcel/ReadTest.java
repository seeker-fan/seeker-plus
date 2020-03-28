package com.wf.seeker.mytest_cases.testEasyExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.metadata.Sheet;

public class ReadTest {

	public static void main(String[] args) throws Exception {
		InputStream inputStream = new FileInputStream(new File(("E:\\excel\\hello.xlsx")));
		List<PersonModel> data = (List<PersonModel>) EasyExcelFactory.read(inputStream,
				new Sheet(1, 1, PersonModel.class));
		inputStream.close();
	}

}
