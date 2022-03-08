package com.example.java.was.model;

public class HttpMethod {
	private String packageName;
	private String methodName;
	private String responseMethod;
	private String[] headers;

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

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

}
