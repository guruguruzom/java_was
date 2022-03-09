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

public class RequestProcessor implements Runnable {
    private final static Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());
    private static final String TEMPLATE_PATH = "\\src\\main\\resources\\templates";
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
            //OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            
            HttpResponse httpResponse = HttpResponseUtil.setHttpResponse(connection.getOutputStream());
            HttpRequest httpRequest = HttpRequestUtil.setHttpRequest(connection.getInputStream());
            //exe 호출 검출
            Boolean isValidateUrl = HttpRequestUtil.isValidateUrl(httpRequest.getUrl());
            
            if(isValidateUrl == false) {
            	//TODO:403 error
            }
            
            //1.file path를 찾는다
            //2.file path는 매핑되어 있다.
            
            try {
            	UrlMapper urlMapper = urlMapperModule.getUrlInfo(httpRequest.getUrl());
            	
            	StringBuilder htmlPath = new StringBuilder();
				htmlPath.append(configSingleton.getDocPath())
						.append(urlMapper.getHtmlPath())
						.append(configSingleton.getSuffix());
				
				String body = ReadFileUtil.getHtmlBody(htmlPath.toString());
				
				//param : responseCode, ontentType
				httpResponse.sendHeader("HTTP/1.1 200 OK", 
										"text/html; charset=utf-8");
				httpResponse.getWriter().write(body);
				
            	httpRequest.setParameter("name", "name");
            	SimpleServlet classMethod = HttpResponseUtil.getClass(urlMapper);
				classMethod.service(httpRequest, httpResponse);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
            //.flush();
            
  
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