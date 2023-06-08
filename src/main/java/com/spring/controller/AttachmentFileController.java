package com.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.dto.AttachmentFile;
import com.spring.dto.Emp;
import com.spring.service.AttachmentFileService;
import com.spring.service.EmpService;

@Controller
public class AttachmentFileController {
	
	@Autowired
	AttachmentFileService attachmentFileService;
	
	@RequestMapping(value = "/download/file/{fileNo}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@PathVariable int fileNo) throws Exception {
		
		/*
		 * 1. fileNo -> DB에 해당 파일이 존재하는지 확인
		 * 2. 			-> 존재한다면 : file -> resource 변환
		 * 3. ResponseEntity<Resource>, header 설정 및 반환	
		 */
		
		// 1.
		AttachmentFile attachmentFile = attachmentFileService.getAttachmentFileByFileNo(fileNo);
		if(attachmentFile == null) {
			throw new Exception("DB에 fileNo에 해당하는 파일이 없습니다.");
		}
		
		// 2.
		Path path = Paths.get("C:\\multi\\00.spring\\files\\" + attachmentFile.getAttachmentFileName()); // 파일의 경로
		Resource resource = null;
		try {
			resource = new InputStreamResource(Files.newInputStream(path)); // path가 없을 수 있으므로 try-catch
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 3
		// Http Header
		HttpHeaders headers = new HttpHeaders();  
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 파일타입이라는 정보를 갖게 된다.
		headers.setContentDisposition(ContentDisposition.builder("attachment") // 첨부파일이다
														.filename(attachmentFile.getAttachmentOriginalFileName()) // 어떤 이름으로 저장 될 것인가?
														.build()); // builder는 build()로 끝난다.
		
		// ResponseEntity<Resource>(리소스, 전달객체정보, HttpStatus.OK)
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}
