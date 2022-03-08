package com.example.java.was.model;

import java.util.Arrays;
import java.util.List;

public class UrlMapper {
	private String url;
	private String packageName;
	private String methodName;
	private String responseMethod;
	private List<String> headers;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getResponseMethod() {
		return responseMethod;
	}

	public void setResponseMethod(String responseMethod) {
		this.responseMethod = responseMethod;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

}
