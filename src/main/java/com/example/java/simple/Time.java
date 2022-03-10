package com.example.java.simple;

import com.example.java.simple.impl.SimpleServlet;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Time implements SimpleServlet {
	
	private static Logger logger = LoggerFactory.getLogger(Time.class);
	
	@Override
	public void service(HttpRequest req, HttpResponse res) throws IOException {
		logger.info("service class Time : " + req.getUrl());
		
		long nowTime = System.currentTimeMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		
		java.io.Writer writer = res.getWriter();
		res.getWriter().write(dateFormat.format(new Date(nowTime)));
	}
}
