package com.spring.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.dto.AttachmentFile;
import com.spring.dto.Dept;
import com.spring.service.AttachmentFileService;
import com.spring.service.DeptService;

import lombok.extern.slf4j.Slf4j;

@Controller
public class DeptController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
	
	@Autowired
	private DeptService service;
	
	@Autowired
	private AttachmentFileService attachmentFileService;
	
	// http://localhost:8082/dept/10
	@RequestMapping(value = "/dept/{deptno}", method = RequestMethod.GET)
	public String getDeptByDeptno(@PathVariable int deptno, 
								  Model model) {
		Dept dept = service.getDeptByDeptno(deptno);
		
		// 해당 부서의 파일 출력 로직 추가!!!
		AttachmentFile attachmentFile = attachmentFileService.getAttachmentFileByDeptno(deptno);
		
		model.addAttribute("dept", dept);
		model.addAttribute("file", attachmentFile);
		
		return "deptDetail";
	}
	
	// http://localhost:8082/dept
	@RequestMapping(value = "/dept", method = RequestMethod.GET)
	public String insertDeptForm() {
		return "registerDept";
	}
	
	// http://localhost:8082/dept
	@RequestMapping(value = "/dept", method = RequestMethod.POST)
	@Transactional
	public String insertDept(@ModelAttribute Dept newDept,
							 @RequestParam("file") MultipartFile file,
							 Model model) {
		String view = "error";
		
		System.out.println(newDept);
		System.out.println(file.getOriginalFilename());
		
		// service.inserDept 성공하면 -> main, 실패하면 -> error
		boolean deptResult = false;
		boolean fileResult = false;
		
		try {
			fileResult = service.insertDept(newDept);
			
			// step01 : 파일 저장 로직도 추가 생성!!!
			fileResult = attachmentFileService.insertAttachmentFile(file, newDept.getDeptno());
			
			if(fileResult && fileResult) {
				view = "redirect:/main";
				return view;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
		
		return view;
	}
	
	// http://localhost:8082/modify/dept/10
	@RequestMapping(value = "/modify/dept/{deptno}", method = RequestMethod.GET)
	public String updateDeptForm(@PathVariable int deptno,
								Model model) {
		Dept dept = service.getDeptByDeptno(deptno);
		
		model.addAttribute("dept", dept);
		
		return "updateDept";
	}
	
	// http://localhost:8082/dept/10
	@RequestMapping(value = "/dept/{deptno}", method = RequestMethod.PUT)
	public String updateDept(@PathVariable int deptno,
							@ModelAttribute("dname") String dname,
							@ModelAttribute("loc") String loc) {
		String view = "error";
		// dname, loc 확인 O
		// deptno로 기존 dept 객체 확인 -> 위에서 확인한 dname, loc 해당 객체 setter
		// 제대로 update가 되었다고 한다면 -> dept/{deptno} detail 페이지로 이동
		boolean result = false;
		
		Dept dept = service.getDeptByDeptno(deptno);
		dept.setDname(dname);
		dept.setLoc(loc);
		
		try {
			 result = service.updateDnameAndLoc(dept);
			 
			 if(result) {
//				 view = "redirect:/dept/" + deptno;
				 return "redirect:/dept/" + deptno;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
		
		return view;
	}
	
	// http://localhost:8082/dept/10
	@RequestMapping(value = "/dept/{deptno}", method = RequestMethod.DELETE)
	@Transactional
	public String deleteDept(@PathVariable int deptno) {
		String view = "error";
		
		System.out.println(deptno);
		
		boolean result = false;
		boolean result2 = false;
		
		try {
			// deleteDeptByDeptno(int deptno)
			result = service.deleteDeptByDeptno(deptno);
			result2 = attachmentFileService.deleteAttachmentFileByDeptno(deptno);
			
			if(result && result2) {
				view = "redirect:/main";
				return view;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
		
		return view;
	}
	
//	// 예외 step02 : LoginController 내부에서 발행하는 모든 NPE 처리
//	@ExceptionHandler({Exception.class})
//	public ModelAndView nullPointerExceptionHandler(Exception exception) {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("exception", exception);
//		mv.setViewName("error");
//		return mv;
//	}
	

	
}
