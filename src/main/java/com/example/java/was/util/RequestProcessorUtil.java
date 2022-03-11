package com.example.java.was.util;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.simple.impl.SimpleServlet;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;
import com.example.java.was.module.ConfigModule;
import com.example.java.was.module.UrlMapperModule;
import com.example.java.was.valueset.ResponseCode;

public class RequestProcessorUtil implements Runnable  {
	private static Logger logger = LoggerFactory.getLogger(RequestProcessorUtil.class);
	
	private static final String PAKAGE_PATH = "com.example.java.simple";
	//도메인별 변화되는 루트값 저장
	private File domainRootDirectory;
	private Socket connection;
	
	public RequestProcessorUtil(File rootDirectory, Socket connection) {
		
		if (rootDirectory.isFile()) {
			throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
		}
		try {
			rootDirectory = rootDirectory.getCanonicalFile();
		} catch (IOException ex) {
		}
		this.connection = connection;
	}

	@Override
	public void run() {
		ConfigModule configModule = ConfigModule.ConfigInstance();
		String rootPath = configModule.getRootPath(connection.getInetAddress().toString());
		
		UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();
		
		domainRootDirectory = new File(configModule.getRootDirectory().getPath() + rootPath);
		String root = domainRootDirectory.getPath();

		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = new HttpResponse();
		
		try {
			httpRequest = HttpRequestUtil.setHttpRequest(connection.getInputStream());
			httpResponse = HttpResponseUtil.setHttpResponse(connection.getOutputStream());
			if(httpRequest == null || httpResponse == null) {
				logger.error("http setting value is null");
				return;
			}
			if(!httpRequest.getMethod().equals("GET")) {
				logger.error("This method is not supported");
				return;
			}
			
			UrlMapper urlMapper = urlMapperModule.getUrlInfo(httpRequest.getUrl());
			
			File filePath = new File(domainRootDirectory,
					urlMapper.getUrl().substring(1, urlMapper.getUrl().length()) + configModule.getSuffix());

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
				htmlPath.append(domainRootDirectory).append(urlMapper.getHtmlPath()).append(configModule.getSuffix());
				
				String body = ReadFileUtil.getHtmlBody(htmlPath.toString());

				// reponseType이 성공일때만 classMethod에 접근
				httpResponse.setReponseCode(ResponseCode.OK);
				httpResponse.setContentType("text/html; charset=utf-8");
				HttpResponseUtil.sendHeader(httpResponse);
				httpResponse.getWriter().write(body);

				// 임시 parameter 처리
				if(httpRequest.getParameter("name") == null) {
					httpRequest.setParameter("name", "{name}");
				}
				
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

	/**
	 * error 발생 시 별도 처리
	 * @param ResponseCode responseCode
	 * @param HttpRequest httpRequest
	 * @param HttpResponse httpResponse
	 * @return
	 * @throws
	 */
	public void serverError(ResponseCode responseCode, HttpRequest httpRequest, HttpResponse httpResponse) {
		ConfigModule configSingleton = ConfigModule.ConfigInstance();
		UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();

		StringBuilder htmlPath = new StringBuilder();
		UrlMapper urlMapper = urlMapperModule.getUrlInfo("/error" + responseCode.getResponseCode());
		htmlPath.append(domainRootDirectory.getParent()).append(urlMapper.getHtmlPath()).append(configSingleton.getSuffix());
		try {
			
			String body = ReadFileUtil.getHtmlBody(htmlPath.toString());
			httpResponse.setReponseCode(responseCode);
			httpResponse.setContentType("text/html; charset=utf-8");
			httpResponse.setLength(body.length());
			HttpResponseUtil.sendHeader(httpResponse);
			httpResponse.getWriter().write(body);
			httpResponse.getWriter().flush();
			//httpResponse.getWriter().close();
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage(), e1);
			e1.printStackTrace();
		}

	}
}
