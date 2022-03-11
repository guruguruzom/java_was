package com.example.java.was.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.was.model.HttpRequest;

public class HttpRequestUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
	private final static String[] stopwords = new String[] { ".exe", "/.." };

	/**
	 * request 초기 설정
	 * 
	 * @param InputStream inputStream : socket의 inputStream 정보
	 * @return HttpRequest : 실패 시 null 반환
	 * @throws UnsupportedEncodingException, IOException
	 */
	public static HttpRequest setHttpRequest(InputStream inputStream) throws UnsupportedEncodingException, IOException {
		HttpRequest httpRequest = null;

		Reader in = new InputStreamReader(new BufferedInputStream(inputStream), "UTF-8");
		StringBuilder requestLine = new StringBuilder();
		while (true) {
			int c = in.read();
			if (c == '\r' || c == '\n')
				break;
			requestLine.append((char) c);
		}
		// String get : ex) GET /basic HTTP/1.1
		String token = requestLine.toString();
		String[] tokens = token.split("\\s+");
		if (tokens.length == 3) {
			httpRequest = new HttpRequest();
			httpRequest.setMethod(tokens[0]);
			httpRequest.setUrl(tokens[1]);
			SetParam(httpRequest);
		} else {
			logger.warn("request info missing" + token);
		}

		return httpRequest;
	}
	
	/**
	 * 
	 * */
	public static HttpRequest SetParam(HttpRequest httpRequest) {
		Map<String, String> param = new HashMap<String, String>();
		
		//파라미터 ?기준으로 이등분
		String[] parameter = httpRequest.getUrl().split("\\?");
		
		if(parameter.length == 1) {
			return httpRequest;
		}
		if(parameter.length > 2) {
			logger.warn("bad request");
			return httpRequest;
		}
		
		//키값 검출
		httpRequest.setUrl(parameter[0]);
		parameter = parameter[1].split("&");
		for(String keyValueStr : parameter) {
			String[] keyValue = keyValueStr.split("=");
			if(keyValue.length == 1 || keyValue.length > 2) {
				logger.warn("bad request");
				break;
			}
			param.put(keyValue[0], keyValue[1]);
		}
		
		httpRequest.setParam(param);
		return httpRequest;
	}

	/**
	 * 잘못된 url 호출 시 false 반환, browser 상위폴더 요청 문제로 '/' 별도 처리
	 * 
	 * @param String url
	 * @return Boolean
	 */
	public static Boolean isValidateUrl(String url) {
		String lowerUrl = url.toLowerCase();
		for (String stopword : stopwords) {
			if (lowerUrl.contains(stopword)) {
				return false;
			}
			if (lowerUrl.equals("/") || lowerUrl.isBlank()) {
				return false;
			}
		}
		return true;
	}
}
