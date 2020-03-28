package com.wf.seeker.mytest_cases.testEasyExcel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;

/****
 * 
 * @author Fan.W
 * @since 1.7
 */
public class EasyExcelTest {

	public static void main(String[] args) throws Exception {
		// test1();
		// test2();
		// test3();
		// test4();
		test5();
	}

	/**
	 * 无表头
	 * 
	 * @throws IOException
	 */
	public static void test1() throws IOException {
		try (OutputStream out = new FileOutputStream("D:\\test1.xlsx");) {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, false);
			Sheet sheet1 = new Sheet(1, 0);
			sheet1.setSheetName("sheet1");
			List<List<String>> data = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				List<String> item = new ArrayList<>();
				item.add("item0" + i);
				item.add("item1" + i);
				item.add("item2" + i);
				data.add(item);
			}
			writer.write0(data, sheet1);
			writer.finish();
			System.out.println("success!!!");
		}
	}

	/**
	 * 带表头
	 * 
	 * @throws IOException
	 */
	public static void test2() throws IOException {
		try (OutputStream out = new FileOutputStream("D:\\test2.xlsx");) {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			Sheet sheet1 = new Sheet(1, 0);
			sheet1.setSheetName("sheet1");
			List<List<String>> data = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				List<String> item = new ArrayList<>();
				item.add("item0" + i);
				item.add("item1" + i);
				item.add("item2" + i);
				data.add(item);
			}
			List<List<String>> head = new ArrayList<List<String>>();
			List<String> headCoulumn1 = new ArrayList<String>();
			List<String> headCoulumn2 = new ArrayList<String>();
			List<String> headCoulumn3 = new ArrayList<String>();
			headCoulumn1.add("第一列");
			headCoulumn2.add("第二列");
			headCoulumn3.add("第三列");
			head.add(headCoulumn1);
			head.add(headCoulumn2);
			head.add(headCoulumn3);
			Table table = new Table(1);
			table.setHead(head);
			writer.write0(data, sheet1, table);
			writer.finish();
			System.out.println("success!!!");
		}
	}

	/**
	 * 带表头，表头通过实体类注解设置
	 * 
	 * @throws IOException
	 */
	public static void test3() throws IOException {
		try (OutputStream out = new FileOutputStream("D:\\test3.xlsx");) {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			Sheet sheet1 = new Sheet(1, 0, PersonModel.class);
			sheet1.setSheetName("sheet1");
			List<PersonModel> data = new ArrayList<>();
			PersonModel item = null;
			for (int i = 0; i < 100; i++) {
				item = new PersonModel();
				item.setName("name" + i);
				item.setAge("age" + i);
				item.setEmail("email" + i);
				item.setAddress("address" + i);
				item.setSax("sax" + i);
				data.add(item);
			}
			writer.write(data, sheet1);
			writer.finish();
			System.out.println("success!!!");
		}
	}

	/**
	 * 多个sheet，存放数据种类不同
	 * 
	 * @throws IOException
	 */
	public static void test4() throws IOException {
		try (OutputStream out = new FileOutputStream("D:\\test4.xlsx");) {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			/**
			 * 第一个sheet写基本信息
			 */
			Sheet sheet1 = new Sheet(1, 0, PersonModel.class);
			sheet1.setSheetName("基本信息");
			List<PersonModel> data = new ArrayList<>();
			PersonModel item = null;
			for (int i = 0; i < 100; i++) {
				item = new PersonModel();
				item.setName("name" + i);
				item.setAge("age" + i);
				item.setEmail("email" + i);
				item.setAddress("address" + i);
				item.setSax("sax" + i);
				data.add(item);
			}
			writer.write(data, sheet1);

			/**
			 * 第二个sheet写产品信息
			 */
			Sheet sheet2 = new Sheet(2, 0, ProductModel.class);
			sheet2.setSheetName("产品信息");
			List<ProductModel> pros = new ArrayList<>();
			ProductModel pro = null;
			for (int i = 0; i < 100; i++) {
				pro = new ProductModel();
				pro.setId(" pro  id " + i);
				pro.setName(" pro name" + i);
				pro.setCount("pro  count" + i);
				pros.add(pro);
			}
			writer.write(pros, sheet2);

			writer.finish();
			System.out.println("success!!!");
		}
	}

	/**
	 * 一个sheet存放不同类型数据
	 * 
	 * @throws IOException
	 */
	public static void test5() throws IOException {
		try (OutputStream out = new FileOutputStream("D:\\test5.xlsx");) {
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
			Sheet sheet1 = new Sheet(1, 0);
			sheet1.setSheetName("提现手续费汇总");

			/**
			 * 第一部分
			 */
			List<List<String>> head = new ArrayList<List<String>>();
			List<String> headCoulumn1 = new ArrayList<String>();
			List<String> headCoulumn2 = new ArrayList<String>();
			List<String> headCoulumn3 = new ArrayList<String>();
			List<String> headCoulumn4 = new ArrayList<String>();
			headCoulumn1.add("渠道名称");
			headCoulumn2.add("汇总年");
			headCoulumn3.add("汇总月");
			headCoulumn4.add("提现总金额");

			head.add(headCoulumn1);
			head.add(headCoulumn2);
			head.add(headCoulumn3);
			head.add(headCoulumn4);

			Table table1 = new Table(1);
			table1.setHead(head);

			List<List<String>> data = new ArrayList<>();// 多条记录

			List<String> col1 = new ArrayList<>();// 单条记录

			col1.add("京东");
			col1.add("2018");
			col1.add("11");
			col1.add("200000");
			data.add(col1);

			writer.write0(data, sheet1, table1);

			/**
			 * 第二部分
			 */
			head = new ArrayList<List<String>>();
			headCoulumn1 = new ArrayList<String>();
			headCoulumn2 = new ArrayList<String>();
			headCoulumn3 = new ArrayList<String>();
			headCoulumn1.add("订单号");
			headCoulumn2.add("下单时间");
			headCoulumn3.add("账号");
			head.add(headCoulumn1);
			head.add(headCoulumn2);
			head.add(headCoulumn3);
			Table table2 = new Table(2);
			table2.setHead(head);

			data = new ArrayList<>();// 整个数据集合
			// 列集合

			for (int i = 0; i < 100; i++) {
				col1 = new ArrayList<>();
				col1.add("订单号" + i);
				col1.add("下单时间" + i);
				col1.add("账号" + i);
				data.add(col1);
			}

			writer.write0(data, sheet1, table2);
			writer.finish();
			System.out.println("success!!!");
		}
	}
}
