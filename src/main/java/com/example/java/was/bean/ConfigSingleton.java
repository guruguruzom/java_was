package com.example.java.was.bean;

import org.json.simple.JSONObject;

import com.example.java.was.model.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//singleton 관리
public class ConfigSingleton {
	private static ConfigSingleton instance;
	private Config config;
	
	public static ConfigSingleton ConfigBean() {
		if(instance == null) {
			instance = new ConfigSingleton();
		}
		return instance;
	}
	
	public void setConfig(String configString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			config = mapper.readValue(configString, Config.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPort() {
		return config.getPort();
	}

	public String getSuffix() {
		return config.getSuffix();
	}

	public String getErrorPath() {
		return config.getErrorPath();
	}
}
