package com.example.java.was.model;

import java.io.IOException;
import java.io.Writer;

public class HttpResponse {
	private Writer out;
	private String responseCode;
	private String contentType;
	private int length;

	public Writer getOut() {
		return out;
	}

	public void setOut(Writer out) {
		this.out = out;
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

	public void setHeader(String responseCode, String contentType, int length) throws IOException {
		this.out.write(responseCode + "\r\n");
		this.out.write("Server: JHTTP 2.0\r\n");
		this.out.write("Content-length: " + length + "\r\n");
		this.out.write("Content-type: " + contentType + "\r\n\r\n");
		this.out.flush();
	}

}
