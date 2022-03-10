package com.example.java.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.simple.impl.SimpleServlet;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;

public class Hello implements SimpleServlet {

	private static Logger logger = LoggerFactory.getLogger(Hello.class);

	@Override
	public void service(HttpRequest req, HttpResponse res) throws Exception {
		logger.info("service class Hello : " + req.getUrl());

		java.io.Writer writer = res.getWriter();

		writer.write("Hello, ");
		writer.write(req.getParameter("name"));
	}
}
