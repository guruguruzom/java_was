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
    private static final String TEMPLATE_PATH = "\\src\\main\\resources\\templates";
    private static final String PAKAGE_PATH = "com.example.java.simple.controller";
    private File rootDirectory;
    private String indexFileName = "index.html";
    private Socket connection;

    public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException(
                    "rootDirectory must be a directory, not a file");
        }
        try {
            rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException ex) {
        }
        this.rootDirectory = rootDirectory;
        if (indexFileName != null)
            this.indexFileName = indexFileName;
        this.connection = connection;
    }

    @Override
    public void run() {
    	ConfigSingleton configSingleton =  ConfigSingleton.ConfigInstance();
    	UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();
    	
        String root = rootDirectory.getPath();
        try {
            System.out.println(rootDirectory);
            HttpRequest httpRequest = HttpRequestUtil.setHttpRequest(connection.getInputStream());
            HttpResponse httpResponse = HttpResponseUtil.setHttpResponse(connection.getOutputStream());
            
            UrlMapper urlMapper = urlMapperModule.getUrlInfo(httpRequest.getUrl());
            
            File filePath = new File(rootDirectory, urlMapper.getUrl().substring(1, urlMapper.getUrl().length()) + configSingleton.getSuffix());
            
            //잘못된 url 검출
            Boolean isValidateUrl = HttpRequestUtil.isValidateUrl(httpRequest.getUrl());
            
            ResponseCode reponseCode = ResponseCode.OK;
            //root 폴더 접근 및 .exe 실행 방지
            if (!filePath.canRead()
                    || !filePath.getCanonicalPath().startsWith(root)
            		|| !isValidateUrl){
            	//TODO:403 error
            	reponseCode = ResponseCode.FORBIDDEN;
            	urlMapper = urlMapperModule.getUrlInfo("/error403");
            }
            if(urlMapper == null) {
            	//TODO:403 error
            	System.out.println("404");
            	reponseCode = ResponseCode.NOT_FOUND;
            	urlMapper = urlMapperModule.getUrlInfo("/error404");
            }
            
            //mapping된 html 접근
        	StringBuilder htmlPath = new StringBuilder();
			htmlPath.append(rootDirectory)
					.append(urlMapper.getHtmlPath())
					.append(configSingleton.getSuffix());
            
            //1.file path를 찾는다
            //2.file path는 매핑되어 있다.
            try {
            	
				String body = ReadFileUtil.getHtmlBody(htmlPath.toString());
				
				//reponseType이 성공일때만 classMethod에 접근
				if(reponseCode.getReponseType().equals("success")) {
					httpResponse.sendHeader(reponseCode, 
											"text/html; charset=utf-8",
											null);
					
					httpResponse.getWriter().write(body);
					
					//임시 parameter 처리
	            	httpRequest.setParameter("name", "name");
	            	SimpleServlet classMethod = HttpResponseUtil.getClass(urlMapper, PAKAGE_PATH);
					classMethod.service(httpRequest, httpResponse);
					
				} else {
					httpResponse.sendHeader(reponseCode, 
											"text/html; charset=utf-8",
											body.length());
					
					httpResponse.getWriter().write(body);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
  
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error talking to " + connection.getRemoteSocketAddress(), ex);
        } finally {
            try {
                connection.close();
            } catch (IOException ex) {
            }
        }
    }
}