package com.example.java.was.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;

import com.example.java.was.model.HttpResponse;

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
	
	public static Class<?> runMethod(String packageName, String functionName) {
		try {
			Class<?> cls = Class.forName(packageName + "." + functionName);
//			Method m = cls.getDeclaredMethod(functionNameA);
//			m.invoke(obj);
			cls.newInstance();
			return cls; 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
