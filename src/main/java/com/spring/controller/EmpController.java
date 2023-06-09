package com.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.dto.Dept;
import com.spring.dto.Emp;
import com.spring.service.DeptService;
import com.spring.service.EmpService;

@Controller
public class EmpController {

	@Autowired
	private EmpService empService;

	@Autowired
	private DeptService deptService;
	
	@PostMapping(value="/deptof")
	public String getEmpByEname(@ModelAttribute("ename") String ename,
								Model model) {
		String view = "error";
		Emp emp = null;
		Dept dept = null; 
		List<Dept> deptList = new ArrayList<Dept>();
		
		
		try {
			emp = empService.getEmpByEname(ename);
			dept = deptService.getDeptByDeptno(emp.getDeptno());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deptList.add(dept);
		model.addAttribute("deptList",deptList);
		view = "main";
		
		return view;
	}

}
