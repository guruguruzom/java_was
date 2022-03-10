package com.example.java.was;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.java.was.Handler.RequestProcessorHandler;
import com.example.java.was.module.ConfigModule;
import com.example.java.was.module.UrlMapperModule;
import com.example.java.was.util.ReadFileUtil;



/**
 * Created by cybaek on 15. 5. 22..
 */
@SpringBootApplication
public class HttpServer {
	
	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);
	
    private static final int NUM_THREADS = 50;
    private static final String CONFIG_PATH = "\\src\\main\\resources\\";
    private static final String CONFIG_FILE = "config.json";
    private static final String ROOT_PATH = "templates\\";
    private static final String HTTP_METHOD_CONFIG_FILE = "url-mapping.json";
    private final File rootDirectory;
    private final int port;

    public HttpServer(File rootDirectory, int port) throws IOException {
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory
                    + " does not exist as a directory");
        }
        this.rootDirectory = rootDirectory;
        this.port = port;
    }
    
    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
        	logger.info("Accepting connections on port: " + server.getLocalPort());
        	logger.info("Document Root: " + rootDirectory);
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessorHandler(rootDirectory, request);
                    pool.submit(r);
                } catch (IOException ex) {
                	logger.error(ex.getMessage(), ex);
                }
            }
        }
    }

    
    
    public static void main(String[] args) throws Exception, IOException, ParseException{
    	
    	String path = System.getProperty("user.dir");
    	
    	//config 설정
    	ConfigModule configSingleton = ConfigModule.ConfigInstance();
    	configSingleton.setConfig(path + CONFIG_PATH, CONFIG_FILE);
    	
    	//http method mapping
    	JSONArray urlMapperJson = ReadFileUtil.getJsonArray(path + CONFIG_PATH + HTTP_METHOD_CONFIG_FILE);
    	UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();
    	urlMapperModule.setUrlMapper(urlMapperJson);
    	
        // get the Document root
        File docroot;
        try {
            docroot = new File(path + CONFIG_PATH + ROOT_PATH);
        } catch (ArrayIndexOutOfBoundsException ex) {
        	logger.error(ex.getMessage(), ex);
            return;
        }
        // set the port to listen on
        int port;
        try {
            port = configSingleton.getPort();
            if (port < 0 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }
        try {
            HttpServer webserver = new HttpServer(docroot, port);
            webserver.start();
        } catch (IOException ex) {
        	logger.error(ex.getMessage(), ex);
        }
    }
    
}