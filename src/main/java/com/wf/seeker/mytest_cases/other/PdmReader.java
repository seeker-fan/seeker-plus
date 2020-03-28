package com.wf.seeker.mytest_cases.other;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * pdm 增加comment
 * 
 * @author Fan.W
 * @since 1.8
 */
public class PdmReader {

	public static void main(String[] args) throws Exception {
		// 创建SAXReader的对象reader
		SAXReader reader = new SAXReader();
		// 通过reader对象的read方法加载文件,获取docuemnt对象。
		Document document = reader.read(new File("C:\\Users\\Kang.Y\\Desktop\\PhysicalDataModel_1.pdm"));

		String xpath = "/Model/o:RootObject/c:Children/o:Model/c:Tables/o:Table";
		List<Node> tables = document.selectNodes(xpath);
		Node tableName = null;
		Node tablePhysicalOptions = null;
		Node columnName = null;
		Node columnComment = null;
		for (Node table : tables) {
			tableName = (Node) table.selectSingleNode("a:Name");
			tablePhysicalOptions = (Element) table.selectSingleNode("a:PhysicalOptions");
			String comment = tablePhysicalOptions.getText();
			String[] split = comment.split(" COMMENT=");
			if (split.length > 1) {
				tableName.setText(split[1].replaceAll("'", ""));
			}
			tablePhysicalOptions.setText(split[0]);
			List<Node> columns = table.selectNodes("c:Columns/o:Column");
			for (Node column : columns) {
				columnName = (Node) column.selectSingleNode("a:Name");
				columnComment = (Node) column.selectSingleNode("a:Comment");
				if (columnComment != null) {
					columnName.setText(columnComment.getText());
					columnComment.setText("");
				}
			}
		}
		// 用于格式化xml内容和设置头部标签
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置xml文档的编码为utf-8
		format.setEncoding("utf-8");
		Writer out = new FileWriter("E://new.pdm");
		XMLWriter writer = new XMLWriter(out, format);
		writer.write(document);
		writer.close();
	}
}
