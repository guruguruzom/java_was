package com.example.java.was.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${server.error.page}")
	private String errorPath;
	
	@RequestMapping(value = "/error")
	public String errorHandler(HttpServletRequest request) {
		Object errorStatus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		logger.info(errorStatus.toString());
		//System.out.println("11" + errorPath);
		
		if(errorStatus != null) {
			//System.out.println("22" + errorStatus.toString());
		}
		request.setAttribute("errorCode", 2222);
		return errorPath;
	}
}
