package com.example.java.was.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.simple.impl.SimpleServlet;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;
import com.example.java.was.valueset.ResponseCode;

public class HttpResponseUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpResponseUtil.class);

	/**
	 * html response 정보값 세팅
	 * 
	 * @param OutputStream outputStream : connection socket의 outputStream 정보
	 * @return HttpResponse
	 * @throws
	 */
	public static HttpResponse setHttpResponse(OutputStream outputStream) {
		OutputStream raw = new BufferedOutputStream(outputStream);
		Writer out = new OutputStreamWriter(raw);
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setWriter(out);
		return httpResponse;
	}

	/**
	 * html 파일 호출
	 * 
	 * @param UrlMapper urlMapper : url 정보를 담은 class, 추후 method mapping을 고려하여
	 *                  String이 아닌 class param 사용
	 * @param String    packagePath : java 파일의 pakege 정보
	 * @return SimpleServlet
	 * @throws Exception
	 */
	public static SimpleServlet getClass(UrlMapper urlMapper, String packagePath) throws Exception {
		String controllerName = urlMapper.getUrl().replace("/", "");
		Class<SimpleServlet> cls = (Class<SimpleServlet>) Class.forName(packagePath + "." + controllerName);

		// 함수명 설정 실행 interface 요구사항으로 제외
		// Method m = cls.getMethod("service",String.class);
		// m.invoke(obj, "method test");

		return cls.newInstance();
	}
	
	/**
	 * header 전송
	 * 
	 * @param HttpResponse httpResponse
	 * @return 
	 * @throws IOException
	 */
	public static void sendHeader(HttpResponse httpResponse) throws IOException {
		
		httpResponse.getWriter().write("HTTP/1.1 " + httpResponse.getResponseCode() + " " + httpResponse.getErrorType() + "\r\n");
		Date now = new Date();
		httpResponse.getWriter().write("Date: " + now + "\r\n");
		httpResponse.getWriter().write("Server: JHTTP 2.0\r\n");
		if(httpResponse.getLength() != null) {
			httpResponse.getWriter().write("Content-length: " + httpResponse.getLength() + "\r\n");
		}
		
		httpResponse.getWriter().write("Content-type: " + httpResponse.getContentType() + "\r\n\r\n");
		httpResponse.getWriter().flush();
	}
}
