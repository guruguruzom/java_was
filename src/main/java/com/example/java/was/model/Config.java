package com.example.java.was.model;

import java.io.IOException;

public class Config {
	/**
	 * 1.port : was 포트 설정 2.suffix : url 구현체 매핑
	 */
	private int port;
	private String suffix;

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
}
