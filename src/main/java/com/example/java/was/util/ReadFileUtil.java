package com.example.java.was.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadFileUtil {
	
	/**
	 * 1.json 파일을 읽어 json object로 반환한다.
	 * 2.json 이외의 파일 경우 method를 추가한다.
	 * @param filePath 읽어 올 파일명
	 * @return JSONObject
	 * @throws IOException, ParseException
	 */
	public JSONObject getJsonFile(String filePath) throws IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		// JSON 파일 읽기
		Reader reader = new FileReader(filePath);
		
		return (JSONObject) parser.parse(reader);

	} 
}
