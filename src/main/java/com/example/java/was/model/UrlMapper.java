package com.example.java.was.model;

import java.util.Arrays;
import java.util.List;

public class UrlMapper {
	private String url;
	private String packageName;
	private String methodName;
	private String htmlPath;
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

	public List<String> getHeaders() {
		return headers;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	
}
