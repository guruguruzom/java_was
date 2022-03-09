package com.example.java.simple.controller;

import com.example.java.simple.controller.impl.SimpleServlet;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;

public class Hello implements SimpleServlet{
	@Override
	public void service(HttpRequest req, HttpResponse res) throws Exception{
		java.io.Writer writer = res.getWriter();
		
		System.out.println("test");
		writer.write("Hello, ");
		writer.write(req.getParameter("name"));
	}
}
