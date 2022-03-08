package com.example.java.was.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.java.was.model.UrlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class UrlMapperModule {
	private static UrlMapperModule instansce;
	private Map<String, UrlMapper> urlMappers = new HashMap<>();

	public static UrlMapperModule ModuleInstance() {
		if (instansce == null) {
			instansce = new UrlMapperModule();
		}
		return instansce;
	}

	public void setUrlMapper(JSONArray urlMapperJson) {
		try {
			//url-mapping json 구조 문제로 바로 map으로 메핑되지 않음
			ObjectMapper mapper = new ObjectMapper();
			for(int i =0;i<urlMapperJson.size();i++) {
				UrlMapper urlMapper = mapper.readValue(urlMapperJson.get(i).toString(), UrlMapper.class);
				
				urlMappers.put(urlMapper.getUrl(), urlMapper);
				//urlMapperJson.get(i);
			}
			System.out.println(urlMappers.toString());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UrlMapper getUrlInfo(String url) {

		return (UrlMapper) urlMappers.get(url);
	}
}
