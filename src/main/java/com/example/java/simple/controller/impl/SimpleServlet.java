package com.example.java.simple.controller.impl;

import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;

public interface SimpleServlet {
	public void service(HttpRequest req, HttpResponse res) throws Exception;
}
