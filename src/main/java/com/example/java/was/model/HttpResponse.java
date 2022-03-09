package com.example.java.was.model;

import java.io.IOException;
import java.io.Writer;

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

	public void sendHeader(String responseCode, String contentType, int length) throws IOException {
		this.writer.write(responseCode + "\r\n");
		this.writer.write("Server: JHTTP 2.0\r\n");
		this.writer.write("Content-length: " + length + "\r\n");
		this.writer.write("Content-type: " + contentType + "\r\n\r\n");
		this.writer.flush();
	}
	
	public void sendHeader(String responseCode, String contentType) throws IOException {
		this.writer.write(responseCode + "\r\n");
		this.writer.write("Server: JHTTP 2.0\r\n");
		this.writer.write("Transfer-Encoding: chunked\r\n");
		this.writer.write("Content-type: " + contentType + "\r\n\r\n");
		this.writer.flush();
	}
}
