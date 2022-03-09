package com.example.java.was.model;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import com.example.java.was.valueset.ResponseCode;

public class HttpResponse {
	private Writer writer;
	private String responseCode;
	private String contentType;
	private int length;

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void sendHeader(ResponseCode reponseCode, String contentType, Integer length) throws IOException {
		
		this.writer.write("HTTP/1.1 " + reponseCode.getResponseCode() + " " + reponseCode.getErrorType() + "\r\n");
		Date now = new Date();
		this.writer.write("Date: " + now + "\r\n");
		this.writer.write("Server: JHTTP 2.0\r\n");
		if(length != null) {
			this.writer.write("Content-length: " + length + "\r\n");
		}
		
		this.writer.write("Content-type: " + contentType + "\r\n\r\n");
		this.writer.flush();
	}
}
