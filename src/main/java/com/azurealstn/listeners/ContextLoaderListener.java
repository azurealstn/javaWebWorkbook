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

import main.java.com.azurealstn.context.ApplicationContext;
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
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			ServletContext sc = sce.getServletContext();
			
			
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			applicationContext = new ApplicationContext(propertiesPath);
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
	}

}
