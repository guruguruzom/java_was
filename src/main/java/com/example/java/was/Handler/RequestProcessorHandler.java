package com.example.java.was.Handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.java.was.module.ConfigModule;
import com.example.java.was.util.RequestProcessorUtil;

public class RequestProcessorHandler implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(RequestProcessorHandler.class);

	private static final int NUM_THREADS = 50;
	
	@Override
	public void run() {
		ConfigModule configModule = ConfigModule.ConfigInstance();
		ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
		try (ServerSocket server = new ServerSocket(configModule.getPort())) {
			logger.info("Accepting connections on port: " + server.getLocalPort());
			logger.info("Document Root: " + configModule.getRootDirectory());
			while (true) {
				try {
					Socket request = server.accept();
					//RequestProcessor가 RequestProcessorHandlerd와 같은 thread 상에 존재한다면 host 변경이 불가능함
					Runnable r = new RequestProcessorUtil(configModule.getRootDirectory(), request);
					pool.submit(r);
				} catch (IOException ex) {
					logger.error(ex.getMessage(), ex);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}