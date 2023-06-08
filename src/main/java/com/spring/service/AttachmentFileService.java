package com.spring.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.dto.AttachmentFile;
import com.spring.dto.Dept;
import com.spring.mapper.AttachmentFileMapper;
import com.spring.mapper.DeptMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttachmentFileService {
	
	@Autowired
	AttachmentFileMapper attachmentFileMapper;
	
	// 객체
	public AttachmentFile getAttachmentFileByFileNo(int fileNo) throws SQLException { 
		System.out.println("찾고자 하는 fileNo = " + fileNo);
		AttachmentFile attachmentFile = null;
		
		attachmentFile = attachmentFileMapper.getAttachmentFileByFileNo(fileNo);
		
		return attachmentFile;
	}
	
	// insert - 
	public boolean insertAttachmentFile(MultipartFile file, int deptno) throws SQLException, Exception {
		boolean result = false;
		
		if(file == null) {
			throw new Exception("파일이 존재하지 않습니다.");
		}
		
		String filePath = "C:\\\\multi\\\\00.spring\\\\files";
		String attachmentOriginalFileName = file.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String attachmentFileName = uuid + "_" + attachmentOriginalFileName;
		Long attachmentFileSize = file.getSize();
		
		// builder로 생성자 만들어서 사용하기
		AttachmentFile attachmentFile = AttachmentFile.builder()
														.filePath(filePath)
														.attachmentOriginalFileName(attachmentOriginalFileName)
														.attachmentFileName(attachmentFileName)
														.attachmentFileSize(attachmentFileSize)
														.deptno(deptno)
														.build();
		
		int res = attachmentFileMapper.insertAttachmentFile(attachmentFile);
		
	

		if(res != 0) {
			// 실제 서버에 저장되는 로직 추가!!! (attachmentFile.trasferTo())
			try {
				if(! new File(filePath).exists()) {
					new File(filePath).mkdir(); // 폴더가 존재하지 않는다면 생성한다.
				}
				
				// 파일 저장(생성), 같은 이름으로 중복 저장될 경우 "덮어쓰기" 된다.
				file.transferTo(new File(filePath + "\\" + attachmentFileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			result = true;
		} else {
			throw new Exception("부서파일 저장 실패");
		}
		
		return result;
	}

	public AttachmentFile getAttachmentFileByDeptno(int deptno) {
		AttachmentFile attachmentFile = null;
		
		attachmentFile = attachmentFileMapper.getAttachmentFileByDeptno(deptno);
		
		return attachmentFile;
	}

	public boolean deleteAttachmentFileByDeptno(int deptno) throws Exception {
		boolean result = false;
		
		
		int res = attachmentFileMapper.deleteAttachmentFileByDeptno(deptno);
		
		if(res != 0) {
			result = true;
		} else {
			throw new Exception("첨부파일 삭제 실패");
		}
		
		return result;
	}

	
	
	
}
