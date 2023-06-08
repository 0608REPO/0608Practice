package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dto.Dept;
import com.spring.mapper.DeptMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeptService {
	
	@Autowired
	DeptMapper mapper;
	
	
	// List
	public List<Dept> getAllDepts() {
		return mapper.getAllDepts();
	}

	// 객체
	public Dept getDeptByDeptno(int deptno) {
		Dept dept = mapper.getDeptByDeptno(deptno);
		
//		if(dept == null) {
//			throw new NullPointerException("존재하지 않는 부서입니다.");
//		}
		
		return dept;
	}
	
	// insert - Dept
	public boolean insertDept(Dept dept) throws SQLException, Exception {
		boolean result = false;
		
		int res = mapper.insertDept(dept);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("부서 생성 실패");
		}
		
		return result;
	}
	
	// update-dept
	public boolean updateDnameAndLoc(Dept dept) throws SQLException, Exception {
		boolean result = false;
		
		int res = mapper.updateDnameAndLoc(dept);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("부서 수정 실패");
		}
		
		return result;
	}
	
	// delete - dept
	public boolean deleteDeptByDeptno(int deptno) throws SQLException, Exception {
		boolean result = false;
		
		int res = mapper.deleteDeptByDeptno(deptno);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("부서 삭제 실패");
		}
		
		return result;
	}

	public List<Dept> searchDynamicDept(HashMap<String, Object> searchDept) {
		return mapper.searchDynamicDept(searchDept);
	}

	
}
