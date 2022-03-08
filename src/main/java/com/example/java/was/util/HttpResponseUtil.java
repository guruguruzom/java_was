package com.example.java.was.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
	
	
}
