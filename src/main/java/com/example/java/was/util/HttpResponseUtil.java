package com.example.java.was.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;

import com.example.java.simple.impl.SimpleServlet;
import com.example.java.was.controller.TestController;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;

public class HttpResponseUtil {
	
	
	
	public static HttpResponse setHttpResponse(OutputStream outputStream) {
		OutputStream raw = new BufferedOutputStream(outputStream);
        Writer out = new OutputStreamWriter(raw);
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setWriter(out);
        return httpResponse;
	}
	
	
	public static SimpleServlet getClass(UrlMapper urlMapper, String packagePath) {
		try {

			String controllerName = urlMapper.getUrl().replace("/", "");
			Class<SimpleServlet> cls = (Class<SimpleServlet>)Class.forName(packagePath + "." + controllerName);
			
			//함수명 설정 실행 interface 요구사항으로 제외
			//Method m = cls.getMethod("service",String.class);
			//m.invoke(obj, "method test");
			
			return cls.newInstance(); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}

