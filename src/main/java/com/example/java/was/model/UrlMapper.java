package com.example.java.was.model;

public class UrlMapper {
	/**
	 * type			name			info
	 * String		url 			user가 호출하는 url string
	 * String		htmlPath 		root/resources/tamplates 내부의 html 파일 path
	 * */
	private String url;
	private String htmlPath;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

}
