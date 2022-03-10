package com.example.java.was;

import java.io.*;
import java.net.Socket;
import org.json.simple.parser.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.simple.impl.SimpleServlet;
import com.example.java.was.bean.ConfigSingleton;
import com.example.java.was.bean.UrlMapperModule;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;
import com.example.java.was.util.HttpRequestUtil;
import com.example.java.was.util.HttpResponseUtil;
import com.example.java.was.util.ReadFileUtil;
import com.example.java.was.valueset.ResponseCode;

public class RequestProcessor implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
	
	private static final String PAKAGE_PATH = "com.example.java.simple";
	private File rootDirectory;
	private Socket connection;

	public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
		if (rootDirectory.isFile()) {
			throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
		}
		try {
			rootDirectory = rootDirectory.getCanonicalFile();
		} catch (IOException ex) {
		}
		this.rootDirectory = rootDirectory;
		this.connection = connection;
	}

	@Override
	public void run() {
		ConfigSingleton configSingleton = ConfigSingleton.ConfigInstance();
		UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();

		String root = rootDirectory.getPath();

		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = new HttpResponse();
		try {
			httpRequest = HttpRequestUtil.setHttpRequest(connection.getInputStream());
			httpResponse = HttpResponseUtil.setHttpResponse(connection.getOutputStream());

			UrlMapper urlMapper = urlMapperModule.getUrlInfo(httpRequest.getUrl());
			
			File filePath = new File(rootDirectory,
					urlMapper.getUrl().substring(1, urlMapper.getUrl().length()) + configSingleton.getSuffix());

			// 잘못된 url 검출
			Boolean isValidateUrl = HttpRequestUtil.isValidateUrl(httpRequest.getUrl());
			
			//500 error 강제 발생 테스트
//			if (httpRequest.getUrl().equals("/forced500error")) {
//				List<String> error = new ArrayList<>();
//				String aa = error.get(2);
//			} 
			if (!filePath.getCanonicalPath().startsWith(root) || !isValidateUrl) {
				logger.error(ResponseCode.FORBIDDEN.getErrorType());
				serverError(ResponseCode.FORBIDDEN, httpRequest, httpResponse);
			} else if (!filePath.canRead()) {
				logger.error(ResponseCode.NOT_FOUND.getErrorType());
				//읽을 수 없는 파일이라면 url mapping map에서 삭제
				urlMapperModule.deleteUrlInfo(httpRequest.getUrl());
				serverError(ResponseCode.NOT_FOUND, httpRequest, httpResponse);
			} else {
				StringBuilder htmlPath = new StringBuilder();
				htmlPath.append(rootDirectory).append(urlMapper.getHtmlPath()).append(configSingleton.getSuffix());
				// 1.file path를 찾는다
				// 2.file path는 매핑되어 있다.
				String body = ReadFileUtil.getHtmlBody(htmlPath.toString());

				// reponseType이 성공일때만 classMethod에 접근
				httpResponse.sendHeader(ResponseCode.OK, "text/html; charset=utf-8", null);
				httpResponse.getWriter().write(body);

				// 임시 parameter 처리
				httpRequest.setParameter("name", "name");
				SimpleServlet classMethod = HttpResponseUtil.getClass(urlMapper, PAKAGE_PATH);
				classMethod.service(httpRequest, httpResponse);

			}

			// mapping된 html 접근
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			serverError(ResponseCode.SERVER_ERROR, httpRequest, httpResponse);
		} finally {
			try {
				connection.close();
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}

	public void serverError(ResponseCode responseCode, HttpRequest httpRequest, HttpResponse httpResponse) {
		ConfigSingleton configSingleton = ConfigSingleton.ConfigInstance();
		UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();

		StringBuilder htmlPath = new StringBuilder();
		UrlMapper urlMapper = urlMapperModule.getUrlInfo("/error" + responseCode.getResponseCode());
		htmlPath.append(rootDirectory).append(urlMapper.getHtmlPath()).append(configSingleton.getSuffix());
		try {
			
			String body = ReadFileUtil.getHtmlBody(htmlPath.toString());
			httpResponse.sendHeader(responseCode, "text/html; charset=utf-8", body.length());
			httpResponse.getWriter().write(body);
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage(), e1);
			e1.printStackTrace();
		}

	}
}