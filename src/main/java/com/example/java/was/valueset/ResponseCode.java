package com.example.java.was.valueset;

public enum ResponseCode {
	OK (200,"OK"),
	BAD_REQUEST (400,"Bad Request"),
	FORBIDDEN (403,"Forbidden"),
	NOT_FOUND (404,"Not Found"),
	SERVER_ERROR (500,"Server Error");
	
	int responseCode;
	String errorType;
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getErrorType() {
		return errorType;
	}
	
	
	ResponseCode(int responseCode, String errorType){
		this.responseCode = responseCode;
		this.errorType = errorType;
	}
	
	public static ResponseCode getEnumFromString(int responseCode) throws Exception {
		for(ResponseCode rc : ResponseCode.values()) {
			if(rc.getResponseCode() == responseCode) {
				return rc;
			}
		}
		
		throw new Exception("Invalid ResponseCode");
	}
}
