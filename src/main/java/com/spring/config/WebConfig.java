package com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.spring.interceptor.SessionInterceptor;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = {"com.spring.*"})
public class WebConfig implements WebMvcConfigurer{
	
	// 	default void addInterceptors(InterceptorRegistry registry)
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new SessionInterceptor())
			        .addPathPatterns("/dept/***")
			        .addPathPatterns("/logout")
			        .excludePathPatterns("/main")
			        .excludePathPatterns("/login");
    }
    
}