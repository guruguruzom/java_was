package com.example.java.was.valueset;

public enum HttpMethod {
	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE");
	
	String method = null;
	
	HttpMethod(String method){
		this.method = method;
	}
	
	public String getMethod() {
		return method;
	}
	
	public static HttpMethod getEnumFromString(String value) throws Exception {
		
		for(HttpMethod hm : HttpMethod.values()) {
			if(hm.getMethod().equals(value)) {
				return hm;
			}
		}
		
		throw new Exception("Invalid ServiceCode");
	}
}
