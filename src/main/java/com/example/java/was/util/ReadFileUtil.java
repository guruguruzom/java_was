package com.example.java.was.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadFileUtil {
	
	/**
	 * @param filePath 읽어 올 파일명
	 * @return JSONObject
	 * @throws IOException, ParseException
	 */
	public static JSONObject getJsonObject(String filePath) throws IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		// JSON 파일 읽기
		Reader reader = new FileReader(filePath);
		JSONObject jsonObject = (JSONObject) parser.parse(reader);
		reader.close();
		
		return jsonObject;
	} 
	
	public static JSONArray getJsonArray(String filePath) throws IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		// JSON 파일 읽기
		Reader reader = new FileReader(filePath);
		JSONArray jsonArray = (JSONArray) parser.parse(reader);
		reader.close();
		
		return jsonArray;
	}
	
	public static String getHtmlBody(String filePath) throws IOException, ParseException
	{
		Reader reader = new FileReader(filePath);
		
		StringBuilder requestLine = new StringBuilder();
		int intValueOfChar;
		while ((intValueOfChar = reader.read()) != -1)  {
            requestLine.append((char) intValueOfChar);
        }
		reader.close();
		
		return requestLine.toString();
	} 
}
