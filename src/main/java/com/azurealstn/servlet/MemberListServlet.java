package main.java.com.azurealstn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.vo.Member;

//프론트 컨트롤러 적용
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// 데이터베이스 관련 객체의 참조변수 선언 (패키지: java.sql) -> DAO 사용

		// JDBC API 사용시 예외처리 (예외발생시 ServletException객체에 담아 서블릿컨테이너에 전달)
		try {
			ServletContext sc = this.getServletContext();
			
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			
			//request에 회원목록 데이터를 보관한다.
			req.setAttribute("members", memberDao.selectList());
			
			req.setAttribute("viewUrl", "/member/MemberList.jsp");
			
		} catch (Exception e) {
			throw new ServletException(e);
		} 
	}

}
