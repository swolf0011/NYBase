package com.swolf.libraryxml.xml.demo;

import com.swolf.libraryxml.xml.NYPullParse;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;




/**
 * @author LiuYi-15973602714
 */
public class PullHandlerPerson extends NYPullParse<Person> {

	/**
	 * 保存
	 */
	public void save(OutputStream outputStream, List<Person> list) {
		if (outputStream != null && list != null && list.size() > 0) {
			try {
				XmlSerializer xs = factory.newSerializer(); // 取得XmlSerializer接口对象
				xs.setOutput(outputStream, "UTF-8"); // 设置输出编码
				xs.startDocument("UTF-8", true);// 文档开始，编码为UTF-8，且独立运行
				xs.startTag(null, "persons");
				// 定义根元素开始
				for (Person p : list) {
					xs.startTag(null, "person"); // 定义person元素开始
					xs.attribute("persons/person", "id", p._id + "");

					xs.startTag(null, "name"); // 定义name元素开始
					xs.text(p.name); // 设置name元素内容
					xs.endTag(null, "name"); // 定义name元素结束

					xs.startTag(null, "pwd"); // 定义pwd元素开始
					xs.text(p.pwd); // 设置pwd元素内容
					xs.endTag(null, "pwd"); // 定义pwd元素结束

					xs.startTag(null, "age"); // 定义age元素开始
					xs.text(p.age + ""); // 设置age元素内容
					xs.endTag(null, "age"); // 定义age元素结束

					xs.endTag(null, "person"); // 定义person元素结束
				}
				xs.endTag(null, "persons"); // 定义根元素完结
				xs.endDocument(); // 定义文档结束
				xs.flush();// 清空缓冲区
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析
	 */
	public List<Person> parse(InputStream inputStream) {
		Person p = null; // 定义Person对象
		String elementName = null; // 保存元素名称
		List<Person> list = null;
		try {
			XmlPullParser xpp = factory.newPullParser(); // 创建XmlPullParser
			xpp.setInput(inputStream, "UTF-8"); // 设置输入流
			int eventType = xpp.getEventType(); // 取得操作事件
			while (eventType != XmlPullParser.END_DOCUMENT) { // 如果文档没有结束
				if (eventType == XmlPullParser.START_DOCUMENT) { // 开始文档
					list = new ArrayList<Person>(); // 实例化List集合
				} else if (eventType == XmlPullParser.START_TAG) { // 开始标记
					elementName = xpp.getName(); // 取得元素名称
					if ("person".equals(elementName)) { // 如果是person节点
						p = new Person(); // 实例化对象
						int count = xpp.getAttributeCount();
						for (int i = 0; i < count; i++) {
							if (xpp.getAttributeName(i).equals("id")) {
								p._id = Integer.parseInt(xpp.getAttributeValue(i));
							}
						}
					}
				} else if (eventType == XmlPullParser.END_TAG) { // 结束标记
					if ("person".equals(xpp.getName())) { // 如果是person节点
						list.add(p); // 保存对象
						p = null; // 清空对象
					}
					elementName = "";
				} else if (eventType == XmlPullParser.TEXT) { // 元素内容
					if ("name".equals(elementName)) { // 如果是name节点
						p.name = xpp.getText();// 取出name节点元素
					} else if ("pwd".equals(elementName)) { // 如果是pwd节点
						p.pwd = xpp.getText();// 取出pwd节点元素
					} else if ("age".equals(elementName)) { // 如果是age节点
						p.age = xpp.getText();// 取出age节点元素
					}
				}
				eventType = xpp.next(); // 取得下一个事件码
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
