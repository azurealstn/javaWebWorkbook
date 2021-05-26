package main.java.com.azurealstn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.util.DBConnectionPool;
import main.java.com.azurealstn.vo.Member;


@Component("memberDao")
public class MySqlMemberDao implements MemberDao {
  SqlSessionFactory sqlSessionFactory;

  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public List<Member> selectList(HashMap<String,Object> paramMap) 
  		throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      return sqlSession.selectList("main.java.com.azurealstn.dao.MemberDao.selectList", paramMap);
    } finally {
      sqlSession.close();
    }
  }

  public int insert(Member member) throws Exception  {
  	SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      int count = sqlSession.insert("main.java.com.azurealstn.dao.MemberDao.insert", member);
      sqlSession.commit();
      return count;
    } finally {
      sqlSession.close();
    }
  }

  public Member selectOne(int no) throws Exception { 
  	SqlSession sqlSession = sqlSessionFactory.openSession();
  	try {
  		return sqlSession.selectOne("main.java.com.azurealstn.dao.MemberDao.selectOne", no);
  	} finally {
  		sqlSession.close();
  	}
  }

  public int update(Member member) throws Exception { 
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
    	Member original = sqlSession.selectOne(
    			"main.java.com.azurealstn.dao.MemberDao.selectOne", member.getNo());
    	
    	Hashtable<String,Object> paramMap = new Hashtable<String,Object>();
    	if (!member.getName().equals(original.getName())) {
    		paramMap.put("name", member.getName());
    	}
    	if (!member.getEmail().equals(original.getEmail())) {
    		paramMap.put("email", member.getEmail());
    	}
    	
    	if (paramMap.size() > 0) {
    		paramMap.put("no", member.getNo());
    		int count = sqlSession.update("main.java.com.azurealstn.dao.MemberDao.update", paramMap);
    		sqlSession.commit();
    		return count;
    	} else {
    		return 0;
    	}
    } finally {
      sqlSession.close();
    }
  }
  
  public int delete(int no) throws Exception {  
  	SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      int count = sqlSession.delete("main.java.com.azurealstn.dao.MemberDao.delete", no);
      sqlSession.commit();
      return count;
    } finally {
      sqlSession.close();
    }
  }
  
  public Member exist(String email, String password) throws Exception {
  	HashMap<String,String> paramMap = new HashMap<String,String>();
  	paramMap.put("email", email);
  	paramMap.put("password", password);
  	
  	SqlSession sqlSession = sqlSessionFactory.openSession();
  	try {
  		return sqlSession.selectOne("main.java.com.azurealstn.dao.MemberDao.exist", paramMap);
  	} finally {
  		sqlSession.close();
  	}
  }
}
