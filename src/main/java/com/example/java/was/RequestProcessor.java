package com.example.java.was;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.java.was.bean.ConfigSingleton;
import com.example.java.was.bean.UrlMapperModule;
import com.example.java.was.controller.TestController;
import com.example.java.was.model.HttpRequest;
import com.example.java.was.model.HttpResponse;
import com.example.java.was.model.UrlMapper;
import com.example.java.was.util.HttpRequestUtil;
import com.example.java.was.util.HttpResponseUtil;

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
    	//ConfigSingleton configSingleton =  ConfigSingleton.ConfigInstance();
    	
    	UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();
    	
        String root = rootDirectory.getPath();
        try {
            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            Writer out = new OutputStreamWriter(raw);
            
            HttpResponse httpResponse = HttpResponseUtil.setHttpResponse(connection.getOutputStream());
            
            HttpRequest httpRequest = HttpRequestUtil.setHttpRequest(connection.getInputStream());
            System.out.println(httpRequest.getUrl());
            //exe 호출 검출
            Boolean isValidateUrl = HttpRequestUtil.isValidateUrl(httpRequest.getUrl());
            
            if(isValidateUrl == false) {
            	//TODO:403 error
            }
            //1.file path를 찾는다
            //2.file path는 매핑되어 있다.
            UrlMapper urlMapper = urlMapperModule.getUrlInfo(httpRequest.getUrl());
            Class<?> classMethod = HttpResponseUtil.runMethod(urlMapper);
            //TestController classMethod2;
            //System.out.println("3333");
//			try {
//				
//				//classMethod2 = classMethod.newInstance();
//				System.out.println("444");
//				//classMethod2.textBasic();
//				System.out.println("555");
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            
            //Class<TestController> test = classMethod;  
            //textBasic()
            //String path = System.getProperty("user.dir") + TEMPLATE_PATH;
//            
//            HttpResponse.runMethod()
//            if (method.equals("GET")) {
//                String fileName = tokens[1];
//                if (fileName.endsWith("/")) fileName += indexFileName;
//                String contentType =
//                        URLConnection.getFileNameMap().getContentTypeFor(fileName);
//                if (tokens.length > 2) {
//                    version = tokens[2];
//                }
//                File theFile = new File(rootDirectory, fileName.substring(1, fileName.length()));
//                if (theFile.canRead()
//// Don't let clients outside the document root
//                        && theFile.getCanonicalPath().startsWith(root)) {
//                    byte[] theData = Files.readAllBytes(theFile.toPath());
//                    if (version.startsWith("HTTP/")) { // send a MIME header
//                        sendHeader(out, "HTTP/1.0 200 OK", contentType, theData.length);
//                    }
//                    // send the file; it may be an image or other binary data
//                    // so use the underlying output stream
//                    // instead of the writer
//                    raw.write(theData);
//                    raw.flush();
//                } else {
//                    // can't find the file
//                    String body = new StringBuilder("<HTML>\r\n")
//                            .append("<HEAD><TITLE>File Not Found</TITLE>\r\n")
//                            .append("</HEAD>\r\n")
//                            .append("<BODY>")
//                            .append("<H1>HTTP Error 404: File Not Found</H1>\r\n")
//                            .append("</BODY></HTML>\r\n")
//                            .toString();
//                    if (version.startsWith("HTTP/")) { // send a MIME header
//                        sendHeader(out, "HTTP/1.0 404 File Not Found", "text/html; charset=utf-8", body.length());
//                    }
//                    out.write(body);
//                    out.flush();
//                }
//            } else {
//                // method does not equal "GET"
//                String body = new StringBuilder("<HTML>\r\n").append("<HEAD><TITLE>Not Implemented</TITLE>\r\n").append("</HEAD>\r\n")
//                        .append("<BODY>")
//                        .append("<H1>HTTP Error 501: Not Implemented</H1>\r\n")
//                        .append("</BODY></HTML>\r\n").toString();
//                if (version.startsWith("HTTP/")) { // send a MIME header
//                    sendHeader(out, "HTTP/1.0 501 Not Implemented",
//                            "text/html; charset=utf-8", body.length());
//                }
//                out.write(body);
//                out.flush();
//            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error talking to " + connection.getRemoteSocketAddress(), ex);
        } finally {
            try {
                connection.close();
            } catch (IOException ex) {
            }
        }
    }

    private void sendHeader(Writer out, String responseCode, String contentType, int length)
            throws IOException {
        out.write(responseCode + "\r\n");
        Date now = new Date();
        out.write("Date: " + now + "\r\n");
        out.write("Server: JHTTP 2.0\r\n");
        out.write("Content-length: " + length + "\r\n");
        out.write("Content-type: " + contentType + "\r\n\r\n");
        out.flush();
    }
}