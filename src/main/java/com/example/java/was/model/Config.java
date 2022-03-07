package com.example.java.was.model;

import java.io.IOException;

public class Config {
	/**
	 * 1.port : was 포트 설정
	 * 2.suffix : url 구현체 매핑
	 * 3.errorPath : error 출력 시 이동 페이지
	 */
	private int port;
	private String suffix;
	private String errorPath;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getErrorPath() {
		return errorPath;
	}

	public void setErrorPath(String errorPath) {
		this.errorPath = errorPath;
	}
	
	

}
