package com.example.java.simple.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.java.simple.controller.impl.SimpleServlet;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;

public class Hello implements SimpleServlet{
	@Override
	public void service(HttpRequest req, HttpResponse res) throws Exception{
		java.io.Writer writer = res.getWriter();
		
		writer.write("Hello, ");
		writer.write(req.getParameter("name"));
	
			List<String> error = new ArrayList<>();
			String aa = error.get(2);
		
	}
}
