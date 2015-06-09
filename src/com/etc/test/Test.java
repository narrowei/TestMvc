package com.etc.test;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

import com.etc.config.ActionMappings;

public class Test {
	public static void main(String[] args) throws IOException, SAXException {
		//1.获取解析器
		Digester digester = DigesterLoader.createDigester(Test.class.getClassLoader().getResource("com/etc/config/rule.xml"));
		//2.push跟节点对应的java对象
		ActionMappings mappings = new ActionMappings();
		digester.push(mappings);
		//3.解析配置文件
		digester.parse(Test.class.getClassLoader().getResource("easystruts-config.xml"));
		
		System.out.println(mappings);
	}
}
