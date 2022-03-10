package com.example.java.was;


import static org.assertj.core.api.Assertions.assertThat;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.java.was.model.Config;
import com.example.java.was.util.ReadFileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootTest
@RunWith(SpringRunner.class) // (1)
class ApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);
	
	private static final String CONFIG_PATH = "\\src\\main\\resources\\";
	private static final String CONFIG_FILE = "config.json";
	
	private Config config;
	/*
	 * 1. HTTP/1.1 의 Host 헤더를 해석하세요.
	 *  - C:\Windows\System32\drivers\etc 파일 추가로 설정
	 * */
	@Test
	public void testVirtualHost() throws Exception {
		responseApi("http://localhost",config.getPort(),"/Hello", HttpStatus.OK);
		responseApi("http://localhost",config.getPort(),"/Time", HttpStatus.OK);
	}
	
	/*
	 * 2. 다음 사항을 설정 파일로 관리하세요
	 *  - config.json에서 port와 suffix관리
	 *  - 에러 페이지의 경우 url-mapping/json에서 관리
	 * */
	@Test
	public void testTemplate() throws Exception {
		responseApi("http://localhost",config.getPort(),"/Time", HttpStatus.OK);
	}
	
	/*
	 * 3. 403, 404, 500 오류를 처리합니다
	 *  - config.json에서 port와 suffix관리
	 *  - 에러 페이지의 경우 url-mapping/json에서 관리
	 * 4. 다음과 같은 보안 규칙을 둡니다.
	 * 
	 * error 페이지 호출 시 junit 실패처리됨
	 * */
	@Test
	public void testErrorPage() throws Exception {
		
		//404 html이 존재하지 않는 페이지 호출
		responseApi("http://localhost",config.getPort(),"/not", HttpStatus.NOT_FOUND);
		//403 error 상위폴더 접근
		responseApi("http://localhost",config.getPort(),"/../", HttpStatus.OK);
		//403 .exe 호출
		responseApi("http://localhost",config.getPort(),"/.exe", HttpStatus.FORBIDDEN);
		//500 .exe 호출
		//responseApi("http://localhost",config.getPort(),"/forced500error", HttpStatus.FORBIDDEN);
	}
	
	/*
	 * 5. logback 프레임워크 http://logback.qos.ch/를 이용하여 다음의 로깅 작업을 합니다.
	 *  - C:\logs\logback에 날짜별 저장
	 * */
	@Test
	public void testLogback() throws Exception {
		logger.debug("log test");
	}
	
	/*
	 * 6. 간단한 WAS 를 구현합니다
	 *  - package 경로의 경우 html과 매핑되지 않아 별도 html 파일을 생성하여 처리
	 * */
	@Test
	public void testSimple() throws Exception {
		responseApi("http://localhost",config.getPort(),"/Hello", HttpStatus.OK);
		responseApi("http://localhost",config.getPort(),"/service.Hello", HttpStatus.OK);
	}
	
	/*
	 * 7. 현재 시각을 출력하는 SimpleServlet 구현체를 작성하세요. 
	 * */
	@Test
	public void testTime() throws Exception {
		responseApi("http://localhost",config.getPort(),"/Time", HttpStatus.OK);
	}
	
	@BeforeEach
	public void setConfig() throws Exception{
		String path = System.getProperty("user.dir");
		JSONObject configJson = ReadFileUtil.getJsonObject(path + CONFIG_PATH + CONFIG_FILE);
		ObjectMapper mapper = new ObjectMapper();
		config = mapper.readValue(configJson.toString(), Config.class);
	}
	
	private void responseApi(String ip,int port, String url, HttpStatus responseType) throws Exception{
		RestTemplate restTemplate = new RestTemplate();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(ip).append(":").append(port).append(url);
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(stringBuilder.toString(), String.class);
		//assertThat(responseEntity.getStatusCode()).isEqualTo(responseType);
	}
	
	

}
