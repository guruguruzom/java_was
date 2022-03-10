package com.example.java.was.model;

import java.io.Writer;

import com.example.java.was.valueset.ResponseCode;

public class HttpResponse {
	/**
	 * type			name			info
	 * Writer		writer			file 내용 작성을 위한 writer, java class 전송을 위함
	 * String		contentType  	header contentType
	 * ResponseCode	reponseCode  	header respo
	 * Integer		length 		 	header 길이, class 접근 시 알 수 없으므로 
	 */
	private Writer writer;
	private String contentType;
	private ResponseCode reponseCode;
	private Integer length = null;

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public int getReponseCode() {
		return reponseCode.getResponseCode();
	}
	
	public int getResponseCode() {
		return reponseCode.getResponseCode();
	}

	public String getErrorType() {
		return reponseCode.getErrorType();
	}

	public void setReponseCode(ResponseCode reponseCode) {
		this.reponseCode = reponseCode;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
