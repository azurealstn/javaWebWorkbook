package main.java.com.azurealstn.dao;

import java.util.HashMap;
import java.util.List;

import main.java.com.azurealstn.vo.Project;

public interface ProjectDao {
	
	//프로젝트 목록 반환
	List<Project> selectList(HashMap<String, Object> paramMap) throws Exception;

	//프로젝트 등록
	int insert(Project project) throws Exception;
	
	//프로젝트 수정
	Project selectOne(int no) throws Exception;
	int update(Project project) throws Exception;
	
	//프로젝트 삭제
	int delete(int no) throws Exception;
}
