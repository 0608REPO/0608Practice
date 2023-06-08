package com.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeAPIController {
	
	// @Value : 만약 bean으로 등록되지 않은 클래스에서 실행되게 된다면, 값을 읽어올 수 없다. (ex)Dept.java에서 실행하면 null 값을 갖게 된다 )
	// 만약 dept.java에서 사용하고 싶다면 @Component로 bean으로 만든 후, Controller에서 @Autowired로 Dept 객체를 bean으로 가져와 하면 @Value가 적용된다.
	@Value("${props.name}") 
	private String propsName;
	
	@RequestMapping(value = "/api/home")
	public String home() {
		System.out.println("home test");
		System.out.println("propsName : " + propsName);
		
		
		
		return "home";
	}
	
}