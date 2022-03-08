package com.example.java.was.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadFileUtil {
	
	/**
	 * @param filePath 읽어 올 파일명
	 * @return JSONObject
	 * @throws IOException, ParseException
	 */
	public static String getJsonFile(String filePath) throws IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		// JSON 파일 읽기
		Reader reader = new FileReader(filePath);
		JSONObject jsonObject = (JSONObject) parser.parse(reader);
		return jsonObject.toString();

	} 
}
