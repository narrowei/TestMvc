package com.etc.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

import com.etc.config.ActionMapping;
import com.etc.config.ActionMappings;
import com.etc.config.ForwardBean;

/**
 * Servlet 只会创建一次 
 * 创建时机  默认的第一次请求到来时创建
 *    	        或者 在web.xml 配置	<load-on-startup>1</load-on-startup> 会在tomcat启动时创建servlet
 * @author Administrator
 *
 */
public class ActionServlet extends HttpServlet {
	ActionMappings mappings;
	public ActionServlet(){
		mappings = new ActionMappings();
		System.out.println("ActionServlet");
	}
	@Override
	public void init() throws ServletException {
		System.out.println("init");
		//1.获取解析器
		Digester digester = DigesterLoader.createDigester(ActionServlet.class.getClassLoader().getResource("com/etc/config/rule.xml"));
		//2.push跟节点对应的java对象
		digester.push(mappings);
		//3.解析配置文件
		try {
			digester.parse(ActionServlet.class.getClassLoader().getResource("easystruts-config.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("service");
		String uri = request.getRequestURI();
		//http://localhost:8080/mvc/doOne.do
		System.out.println(uri); // uri = /mvc/doOne.do
		String path = uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
		System.out.println(path);
		
		//依据 path 查找配置文件，找到 type 
		ActionMapping mapping = mappings.findActionMapping(path);
		String type = mapping.getType();
		//反射，创建action实例
		try {
			Action action = (Action)Class.forName(type).newInstance();
			//可以调用action中的execute方法
			//得到结果String --》找配置文件，选择视图，进行转发/重定向
			String strForward = action.execute(request, response);
			ForwardBean fb = mapping.findForward(strForward);
			if(fb.isRedirect()){
				response.sendRedirect(request.getContextPath()+fb.getPath());
			}else{
				request.getRequestDispatcher(fb.getPath()).forward(request, response);
			}
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if("/doOne".equals(path)){
//			// 调用javabean
//			Action action = new DoOneAction();
//			String str = action.execute(request, response);
//			//转发
//			request.getRequestDispatcher(str).forward(request, response);
//		}
//		if("/doTwo".equals(path)){
//			// 调用javabean
//			Action action = new DoTwoAction();
//			String str = action.execute(request, response);
//			//重定向
//			response.sendRedirect(str);
//		}
	}

}
