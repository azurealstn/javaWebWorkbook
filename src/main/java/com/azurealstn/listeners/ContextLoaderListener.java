package main.java.com.azurealstn.listeners;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import main.java.com.azurealstn.dao.MemberDao;
import main.java.com.azurealstn.util.DBConnectionPool;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	DBConnectionPool connPool;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		connPool.closeAll();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext sc = sce.getServletContext();
			
			connPool = new DBConnectionPool(
					sc.getInitParameter("driver"),
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDbConnectionPool(connPool);
			
			sc.setAttribute("memberDao", memberDao);
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}

}
