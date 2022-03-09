package com.example.java.was.valueset;

public enum ResponseCode {
	OK (200,"OK","success"),
	BAD_REQUEST (400,"Bad Request","failer"),
	FORBIDDEN (403,"Forbidden","failer"),
	NOT_FOUND (404,"Not Found","failer"),
	SERVER_ERROR (500,"Server Error","failer");
	
	int responseCode;
	String errorType;
	String reponseType;
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getErrorType() {
		return errorType;
	}
	
	public String getReponseType() {
		return reponseType;
	}
	
	ResponseCode(int responseCode, String errorType, String reponseType){
		this.responseCode = responseCode;
		this.errorType = errorType;
		this.reponseType = reponseType;
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
