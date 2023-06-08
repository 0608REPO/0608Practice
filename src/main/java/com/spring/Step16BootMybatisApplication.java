package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Step16BootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Step16BootMybatisApplication.class, args);
	}
	
	// 1. properties에 적어놓은 설정들이 WebConfig를 적용한 이후 작동하지 않는 문제가 있다.
	// -> resolver(prefix, suffix), 파일 업로드 가능/다운로드 불가능(multipart), 데이터 삭제 불가(hidden으로 추정)
	
}
