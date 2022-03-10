package com.example.java.was.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.was.Handler.RequestProcessorHandler;

public class ReadFileUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ReadFileUtil.class);
	/**
	 * config json object 호출 
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
	
	/**
	 * url mapping jsonArray 호출 
	 * @param String filePath : 읽어 올 파일명
	 * @return JSONArray
	 * @throws IOException, ParseException
	 */
	public static JSONArray getJsonArray(String filePath) throws IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		// JSON 파일 읽기
		Reader reader = new FileReader(filePath);
		JSONArray jsonArray = (JSONArray) parser.parse(reader);
		reader.close();
		
		return jsonArray;
	}
	
	/**
	 * html 파일 호출
	 * @param String filePath : 읽어 올 파일명
	 * @return String
	 * @throws IOException, ParseException
	 */
	public static String getHtmlBody(String filePath) throws IOException, ParseException
	{
		Reader reader = new FileReader(filePath);
		
		StringBuilder requestLine = new StringBuilder();
		int intValueOfChar;
		//string으로 읽은 후 string build
		while ((intValueOfChar = reader.read()) != -1)  {
            requestLine.append((char) intValueOfChar);
        }
		reader.close();
		
		return requestLine.toString();
	} 
}
