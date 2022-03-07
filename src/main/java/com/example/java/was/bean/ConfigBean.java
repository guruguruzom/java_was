package com.example.java.was.bean;

import com.example.java.was.model.Config;

//singleton 관리
public class ConfigBean {
	Config instance;
	
	public Config ConfigBean() {
		if(instance == null) {
			instance = new Config();
		}
		return instance;
	}
}
