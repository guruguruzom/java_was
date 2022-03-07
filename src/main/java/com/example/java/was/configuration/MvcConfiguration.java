package com.example.java.was.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers (final ResourceHandlerRegistry handlerRegistry) {
		handlerRegistry.addResourceHandler("/**")
						.addResourceLocations("classpath:/templates/");
	}
}
