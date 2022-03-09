package com.example.java.was.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import com.example.java.was.RequestProcessor;
import com.example.java.was.model.HttpRequest;

public class HttpRequestUtil {
	private final static Logger logger = Logger.getLogger(HttpRequestUtil.class.getCanonicalName());
	private final static String[] stopwords = new String[] {".exe", "/.."};
	/*
	 * 
	 * @param InputStream(socket input stream)
	 * return HttpRequest : 실패 시 null 반환 
	 * */
	public static HttpRequest setHttpRequest(InputStream inputStream) {
		HttpRequest httpRequest = null;
		
		try {
			Reader in = new InputStreamReader(new BufferedInputStream(inputStream), "UTF-8");
			StringBuilder requestLine = new StringBuilder();
            while (true) {
                int c = in.read();
                if (c == '\r' || c == '\n')
                    break;
                requestLine.append((char) c);
            }
            //String get : ex) GET /basic HTTP/1.1
            String token = requestLine.toString();
            String[] tokens = token.split("\\s+");
            if(tokens.length == 3) {
            	httpRequest = new HttpRequest();
            	httpRequest.setMethod(tokens[0]);
            	httpRequest.setUrl(tokens[1]);
            } else {
            	logger.warning("request info missing" + token);
            }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return httpRequest;
	}
	
	/*
	 * 잘못된 url 호출 시 false 반환, browser 상위폴더 요청 시 /로 잘려서 보냄. api프로그램을 통 상위폴더 검출 시 실패 반환
	 * @param String url
	 * return Boolean
	 * */
	public static Boolean isValidateUrl(String url) {
		String lowerUrl = url.toLowerCase();
		for(String stopword : stopwords) {
			if(lowerUrl.contains(stopword)) {
				return false;
			}
			if(lowerUrl.equals("/") || lowerUrl.isBlank()) {
				return false;
			}
		}
		return true;
	}
}
