package com.example.java.was;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

import com.example.java.simple.controller.impl.SimpleServlet;
import com.example.java.was.bean.ConfigSingleton;
import com.example.java.was.bean.UrlMapperModule;
import com.example.java.was.controller.TestController;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;
import com.example.java.was.util.HttpRequestUtil;
import com.example.java.was.util.HttpResponseUtil;
import com.example.java.was.util.ReadFileUtil;
import com.example.java.was.valueset.ResponseCode;

public class RequestProcessor implements Runnable {
	private final static Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());
	private static final String PAKAGE_PATH = "com.example.java.simple.controller";
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

			// root 폴더 접근 및 .exe 실행 방지
			if (!filePath.canRead() || !filePath.getCanonicalPath().startsWith(root) || !isValidateUrl) {
				// TODO:403 error
				serverError(ResponseCode.FORBIDDEN, httpRequest, httpResponse);
			} else if (urlMapper == null) {
				// TODO:403 error
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
			logger.log(Level.WARNING, "Error talking to " + connection.getRemoteSocketAddress(), e);
			serverError(ResponseCode.SERVER_ERROR, httpRequest, httpResponse);
		} finally {
			try {
				connection.close();
			} catch (IOException ex) {
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
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}