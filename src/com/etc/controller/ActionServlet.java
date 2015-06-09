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
 * Servlet ֻ�ᴴ��һ�� 
 * ����ʱ��  Ĭ�ϵĵ�һ��������ʱ����
 *    	        ���� ��web.xml ����	<load-on-startup>1</load-on-startup> ����tomcat����ʱ����servlet
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
		//1.��ȡ������
		Digester digester = DigesterLoader.createDigester(ActionServlet.class.getClassLoader().getResource("com/etc/config/rule.xml"));
		//2.push���ڵ��Ӧ��java����
		digester.push(mappings);
		//3.���������ļ�
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
		
		//���� path ���������ļ����ҵ� type 
		ActionMapping mapping = mappings.findActionMapping(path);
		String type = mapping.getType();
		//���䣬����actionʵ��
		try {
			Action action = (Action)Class.forName(type).newInstance();
			//���Ե���action�е�execute����
			//�õ����String --���������ļ���ѡ����ͼ������ת��/�ض���
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
//			// ����javabean
//			Action action = new DoOneAction();
//			String str = action.execute(request, response);
//			//ת��
//			request.getRequestDispatcher(str).forward(request, response);
//		}
//		if("/doTwo".equals(path)){
//			// ����javabean
//			Action action = new DoTwoAction();
//			String str = action.execute(request, response);
//			//�ض���
//			response.sendRedirect(str);
//		}
	}

}
