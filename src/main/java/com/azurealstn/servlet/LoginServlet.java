package main.java.com.azurealstn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.vo.Member;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("viewUrl", "/auth/LoginForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
			
			Member member = memberDao.exist(req.getParameter("email"), req.getParameter("password"));
			
			if (member != null) {
				HttpSession session = req.getSession();
				session.setAttribute("member", member);
				req.setAttribute("viewUrl", "redirect:../member/list.do");
			} else {
				req.setAttribute("viewUrl", "/auth/LoginFail.jsp");
			}

		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

	
}
