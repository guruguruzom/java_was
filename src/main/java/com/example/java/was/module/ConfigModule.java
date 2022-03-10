package com.example.java.was.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.example.java.was.model.ConfigModel;
import com.example.java.was.model.DomainPathModel;
import com.example.java.was.util.ReadFileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//singleton 관리
public class ConfigModule {
	private static ConfigModule instance;
	private ConfigModel config;
	private Map<String, String> hosts = new HashMap<>();
	//스레드 정보 확인
	private Boolean doThread = true;

	public static ConfigModule ConfigInstance() {
		if (instance == null) {
			instance = new ConfigModule();
		}
		return instance;
	}

	public Boolean getDoThread(){
		return doThread;
	}
	
	public void setDoThread (Boolean threadState){
		this.doThread = threadState;
	}
	/**
	 * 초기 설정 값 세팅 port와 suffix 두 가지
	 * @param String path
	 * @param String file
	 * @return
	 * @throws JsonMappingException, JsonProcessingException, IOException,
	 *                               ParseException
	 */
	public void setConfig(String path, String file)
			throws JsonMappingException, JsonProcessingException, IOException, ParseException {

		JSONObject configJson = ReadFileUtil.getJsonObject(path + file);
		
		ObjectMapper mapper = new ObjectMapper();
		config = mapper.readValue(configJson.toString(), ConfigModel.class);
		
		for(DomainPathModel domainPath : config.getHost()) {
			hosts.put(domainPath.getIp(), domainPath.getRootPath());
		}
	}


	public int getPort() {
		return config.getPort();
	}

	public String getSuffix() {
		return config.getSuffix();
	}
	
	public String getRootPath(String ip) {
		String host = hosts.get(ip);
		if(host.isBlank()) {
			host = config.getDefualtHost();
		}
		return host;
	}

}
