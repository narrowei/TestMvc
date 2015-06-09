package com.etc.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etc.controller.Action;

public class DoOneAction extends Action{
	public String execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//Âß¼­´¦Àí
		return "one";
	}
}
