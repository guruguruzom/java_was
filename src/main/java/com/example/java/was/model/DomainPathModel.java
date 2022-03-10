package com.example.java.was.model;

public class DomainPathModel {
	/**
	 * type			name			info
	 * String			ip 			접속 ip주소
	 * String		rootPath 		ROOT 경로
	 * */
	private String ip;
	private String rootPath;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
}
