package com.example.java.was.model;

import com.example.java.was.valueset.HttpMethod;

public class HttpRequest {
	private HttpMethod method;
	private String url;

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(String method) {
		try {
			this.method = HttpMethod.getEnumFromString(method);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "HttpRequest [method=" + method + ", url=" + url + "]";
	}

}
