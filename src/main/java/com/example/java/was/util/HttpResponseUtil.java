package com.example.java.was.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;

import com.example.java.was.controller.TestController;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;

public class HttpResponseUtil {
	
	public static HttpResponse setHttpResponse(OutputStream outputStream) {
		OutputStream raw = new BufferedOutputStream(outputStream);
        Writer out = new OutputStreamWriter(raw);
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setOut(out);
        return httpResponse;
	}
	
	public static void setHeader(HttpResponse httpResponse, String responseCode, String contentType, int length) throws IOException{
		httpResponse.setHeader(responseCode, contentType, length);
	}
	
	public static Class<?> runMethod(UrlMapper urlMapper) {
		try {
			System.out.println(urlMapper.getPackageName() + "." + urlMapper.getControllerName());
			System.out.println(urlMapper.getResponseMethod());
			Class<?> cls = (Class<?>)Class.forName(urlMapper.getPackageName() + "." + urlMapper.getControllerName());
			
			Object obj = cls.newInstance();
			
			Method m = cls.getMethod(urlMapper.getResponseMethod(),String.class);
			//Method m = cls.getMethod(urlMapper.getResponseMethod(),파라미터 타입.class (ex:Integer.class))
//			Method m = cls.getDeclaredMethod(functionNameA);
//			m.invoke(obj);
			m.invoke(obj, "method test");
			//cls.newInstance();
			return cls; 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}

