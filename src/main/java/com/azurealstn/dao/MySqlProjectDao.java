package main.java.com.azurealstn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<Project> selectList(HashMap<String, Object> paramMap) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
	    
	    try {
	      return sqlSession.selectList("main.java.com.azurealstn.dao.ProjectDao.selectList", paramMap);

	    } finally {
	      sqlSession.close();
	    }
	}

	@Override
	public int insert(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

	    try {
	    	int count = sqlSession.insert("main.java.com.azurealstn.dao.ProjectDao.insert", project);
	    	sqlSession.commit();
	    	return count;

	    } finally {
	    	sqlSession.close();
	    }
	}

	@Override
	public Project selectOne(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
	    try {
	    	return sqlSession.selectOne("main.java.com.azurealstn.dao.ProjectDao.selectOne", no);

	    } finally {
	    	sqlSession.close();
	    }
	}

	@Override
	public int update(Project project) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
	    try {
	    	Project original = sqlSession.selectOne(
	    			"main.java.com.azurealstn.dao.ProjectDao.selectOne", project.getNo());
	    	
	    	Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
	    	if (!project.getTitle().equals(original.getTitle())) {
	    		paramMap.put("title", project.getTitle());
	    	}
	    	if (!project.getContent().equals(original.getContent())) {
	    		paramMap.put("content", project.getContent());
	    	}
	    	if (project.getStartDate().compareTo(original.getStartDate()) != 0) {
	    		paramMap.put("startDate", project.getStartDate());
	    	}
	    	if (project.getEndDate().compareTo(original.getEndDate()) != 0) {
	    		paramMap.put("endDate", project.getEndDate());
	    	}
	    	if (project.getState() != original.getState()) {
	    		paramMap.put("state", project.getState());
	    	}
	    	if (!project.getTags().equals(original.getTags())) {
	    		paramMap.put("tags", project.getTags());
	    	}
	    	
	    	if (paramMap.size() > 0) {
	    		paramMap.put("no", project.getNo());
	    		int count = sqlSession.update("main.java.com.azurealstn.dao.ProjectDao.update", paramMap);
	    		sqlSession.commit();
	    		return count;
	    	} else {
	    		return 0;
	    	}
	    } finally {
	      sqlSession.close();
	    }
	}

	@Override
	public int delete(int no) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

	    try {
	    	int count = sqlSession.delete("main.java.com.azurealstn.dao.ProjectDao.delete", no);
	    	sqlSession.commit();
	    	return count;

	    } finally {
	    	sqlSession.close();
	    }
	}

}
