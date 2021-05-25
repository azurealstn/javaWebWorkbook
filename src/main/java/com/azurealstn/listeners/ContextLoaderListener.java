package main.java.com.azurealstn.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import main.java.com.azurealstn.controls.LoginController;
import main.java.com.azurealstn.controls.LogoutController;
import main.java.com.azurealstn.controls.MemberAddController;
import main.java.com.azurealstn.controls.MemberDeleteController;
import main.java.com.azurealstn.controls.MemberListController;
import main.java.com.azurealstn.controls.MemberUpdateController;
import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.util.DBConnectionPool;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext sc = sce.getServletContext();
			
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/study_db");
			
			MySqlMemberDao memberDao = new MySqlMemberDao();
			memberDao.setDataSource(ds);
			
			//setter 주입
			sc.setAttribute("/auth/login.do", new LoginController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new LogoutController());
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}

}
