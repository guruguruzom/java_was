package com.example.java.was.bean;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.example.java.was.model.Config;
import com.example.java.was.util.ReadFileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//singleton 관리
public class ConfigSingleton {
	private static ConfigSingleton instance;
	private Config config;
	
	public static ConfigSingleton ConfigInstance() {
		if(instance == null) {
			instance = new ConfigSingleton();
		}
		return instance;
	}
	
	public void setConfig(String path, String file) {
		
		try {
			JSONObject configJson;
			configJson = ReadFileUtil.getJsonObject(path + file);
			ObjectMapper mapper = new ObjectMapper();
			config = mapper.readValue(configJson.toString(), Config.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
