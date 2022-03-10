package com.example.java.was.model;

import java.util.LinkedList;
import java.util.List;

public class ConfigModel {

	/**
	 * type name info int port was 포트 설정 String suffix url 구현체 파일 확장자
	 */
	private int port;
	private String suffix;
	private String defualtHost;
	private List<DomainPathModel> host = new LinkedList<>();

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

	public String getDefualtHost() {
		return defualtHost;
	}

	public void setDefualtHost(String defualtHost) {
		this.defualtHost = defualtHost;
	}

	public List<DomainPathModel> getHost() {
		return host;
	}

	public void setHost(List<DomainPathModel> host) {
		this.host = host;
	}

}
