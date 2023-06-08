package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.dto.Dept;
import com.spring.service.DeptService;

@Controller
public class HomeController {
	
	@Autowired
	private DeptService service;
	
	@Value("${server.port}") 
	private String portNum;
	
	@RequestMapping(value = "/home")
	public String home() {
		System.out.println("home test");
		
		return "home";
	}
	
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model model, HttpSession session) {
		List<Dept> deptList = service.getAllDepts();
		
		model.addAttribute("deptList", deptList);
		
		if(session.getAttribute("portNum") == null) {
			session.setAttribute("portNum", portNum);
		}
		
		return "main";
	}
}
