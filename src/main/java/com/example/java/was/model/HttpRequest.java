package com.example.java.was.model;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	
	/**
	 * type			name			info
	 * String		method 			Method type(구현되지 않음)
	 * String		url 			url 정보값
	 * Map<S,S>		param			파라미터 정보값(key값으로 받는 부분 구현되지 않음)
	 * */
	private String method;
	private String url;
	private Map<String, String> param = new HashMap<String, String>();

	public String getMethod() {
		return method;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setParameter(String key,String value) {
		param.put(key,value);
	}
	
	public String getParameter(String key) {
		return param.get(key);
	}

	@Override
	public String toString() {
		return "HttpRequest [method=" + method + ", url=" + url + "]";
	}

}
