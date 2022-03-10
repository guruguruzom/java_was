package com.example.java.was.module;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;

import com.example.java.was.model.UrlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UrlMapperModule {
	private static UrlMapperModule instansce;
	private Map<String, UrlMapper> urlMappers = new HashMap<>();

	public static UrlMapperModule ModuleInstance() {
		if (instansce == null) {
			instansce = new UrlMapperModule();
		}
		return instansce;
	}

	/**
	 * url 별 html 파일 및 java 파일 세팅, urlMappers관리
	 * 
	 * @param JSONArray urlMapperJson : ReadFileUtil에서 가져온 json array
	 * @return
	 * @throws JsonMappingException, JsonProcessingException
	 */
	public void setUrlMapper(JSONArray urlMapperJson) throws JsonMappingException, JsonProcessingException {
		// url-mapping json 구조 문제로 바로 map으로 메핑되지 않음
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < urlMapperJson.size(); i++) {
			UrlMapper urlMapper = mapper.readValue(urlMapperJson.get(i).toString(), UrlMapper.class);

			urlMappers.put(urlMapper.getUrl(), urlMapper);
		}

	}

	/**
	 * url 키값에 맞는 html 정보 반환, 정보값이 없다면 urlMappers에 임시 추가
	 * 
	 * @param String url
	 * @return UrlMapper
	 * @throws
	 */
	public UrlMapper getUrlInfo(String url) {
		UrlMapper urlMapper = (UrlMapper) urlMappers.get(url);
		// urlMappers에 키값이 없다면 404이 아닌 키값 추가
		if (urlMapper == null) {
			urlMapper = new UrlMapper();
			urlMapper.setUrl(url);
			urlMapper.setHtmlPath("\\" + url.substring(1, url.length()));
			urlMappers.put(url, urlMapper);
		}
		return urlMapper;
	}

	public void deleteUrlInfo(String url) {
		urlMappers.remove(url);
	}
}
