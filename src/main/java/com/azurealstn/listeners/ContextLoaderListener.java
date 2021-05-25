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

import main.java.com.azurealstn.dao.MemberDao;
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
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);
			
			sc.setAttribute("memberDao", memberDao);
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}

}
