package com.spring.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.Dept;
import com.spring.mapper.DeptMapper;
import com.spring.service.DeptService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DeptAPIController {
		
		final DeptMapper deptMapper;
		final DeptService deptService;// 이것이 훨씬 안전하다.

		
		// http://localhost:8082/api/dynamic-sql
		@RequestMapping(value = "/api/dynamic-sql/{deptno}/{condition}", method = RequestMethod.POST)
		public String getAPIDynamicSQL(@PathVariable("deptno") String deptno,
				 						@PathVariable("condition")String condition, Model model) throws Exception {
			System.out.println("getAPIDynamicSQL()");
			String result = "";
			
			// getDynamicDeptno
			HashMap<String, Integer> map1 = new HashMap<String, Integer>();
			map1.put("more", Integer.parseInt(deptno));
			map1.put("less", Integer.parseInt(condition));
			System.out.println(map1);
			List<Dept> result1 = deptMapper.getConditionalDeptno(map1);
			System.out.println(result1);
			//select * from dept where dname = "SALES" and loc = "DALLAS"
			// getDynamicChoose
			// SELECT * FROM dept WHERE dname = "SALES" and loc = "DALLAS"
//			HashMap<String, String> map2 = new HashMap<String, String>();
//					map2.put("dname", "SALES");
//					map2.put("loc", "DALLAS");
//			
//			List<Dept> result2 = deptMapper.getDynamicChoose(map2);
//					result = result1.toString();
//					HashMap<String, String> map3 = new HashMap<String, String>();
//					map3.put("dname", "SALES");
//					map3.put("loc", "DALLAS");
//			
//			List<Dept> result3= deptMapper.getDynamicWhereTrim(map3);
//					result = result3.toString();
//			HashMap<String, String> map4 = new HashMap<String, String>();
//					map4.put("dname", "SALES");
//					map4.put("loc", "DALLAS");
//			
//			List<Dept> result4= deptMapper.getDynamicWhereTrim2(new Dept(0,null,"DALLAS"));
//					result = result4.toString();			
//					// set
//					// UPDATE dept SET loc = "LA" WHERE deptno = 40
//					HashMap<String, Object> map5 = new HashMap<String, Object>();
//			    		map5.put("deptno", 40);
//							map5.put("dname", null);
//							map5.put("loc", "LA");
//							
//					//foreach
//					//select * from dept where deptno in (10, 20 , 30)
//					Integer result5 = deptMapper.updateDynamicSet(map5);
//					List<Integer> list6 = Arrays.asList(10, 20, 30);
////					List<Dept> result6 = deptMapper.getDynamicDeptnoForeachDeptno(list6);
//					//foreach * : insert는 조금 다르다.
//					/*
//					 * INsert all int dept(deptno, dname, loc ) values (deptno, dname, loc)
//					 * into dept(deptno, dname, loc)values (deptno, dname, loc)
//					 * select * from dual;
//					 * 
//					 * 
//					 */
//					List<Dept> list7 = Arrays.asList(new Dept(89, "PIZZA", "JEJU"),
//							new Dept(91,"DUPBAB","SEOUL"));
//					Integer result7 = deptMapper.insertDynamicForeachDeptList(list7);
//					
//					result = result7.toString();
					result = result1.toString();
					model.addAttribute("deptList", result1);
					System.out.println(result);
					return "main";
		}	
		
		@ExceptionHandler(value = {Exception.class})
		public ResponseEntity<String> handleException(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
}
