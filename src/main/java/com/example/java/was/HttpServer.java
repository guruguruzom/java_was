package com.example.java.was;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.java.was.bean.ConfigSingleton;
import com.example.java.was.bean.UrlMapperModule;
import com.example.java.was.util.ReadFileUtil;

/**
 * Created by cybaek on 15. 5. 22..
 */
@SpringBootApplication
public class HttpServer {
    private static final Logger logger = Logger.getLogger(HttpServer.class.getCanonicalName());
    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";
    private static final String CONFIG_PATH = "\\src\\main\\resources\\";
    private static final String CONFIG_FILE = "config.json";
    private static final String HTTP_METHOD_CONFIG_FILE = "url-mapping.json";
    private static final String WEB_PATH = "templates";
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
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
                    pool.submit(r);
                } catch (IOException ex) {
                    logger.log(Level.WARNING, "Error accepting connection", ex);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception, IOException, ParseException{
    	
    	//SpringApplication.run(HttpServer.class, args);
    	//config setting
    	String path = System.getProperty("user.dir");
    	JSONObject configJson = ReadFileUtil.getJsonObject(path + CONFIG_PATH + CONFIG_FILE);
    	ConfigSingleton configSingleton = ConfigSingleton.ConfigInstance();
    	configSingleton.setConfig(configJson.toString());
    	
    	//http method mapping
    	JSONArray urlMapperJson = ReadFileUtil.getJsonArray(path + CONFIG_PATH + HTTP_METHOD_CONFIG_FILE);
    	UrlMapperModule urlMapperModule = UrlMapperModule.ModuleInstance();
    	urlMapperModule.setUrlMapper(urlMapperJson);
    	
		
        // get the Document root
        File docroot;
        try {
            docroot = new File(path + CONFIG_PATH + WEB_PATH);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Usage: java JHTTP docroot port");
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
            logger.log(Level.SEVERE, "Server could not start", ex);
        }
    }
}