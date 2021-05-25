package main.java.com.azurealstn.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.bind.ServletRequestDataBinder;
import main.java.com.azurealstn.controls.Controller;
import main.java.com.azurealstn.controls.LoginController;
import main.java.com.azurealstn.controls.LogoutController;
import main.java.com.azurealstn.controls.MemberAddController;
import main.java.com.azurealstn.controls.MemberDeleteController;
import main.java.com.azurealstn.controls.MemberListController;
import main.java.com.azurealstn.controls.MemberUpdateController;
import main.java.com.azurealstn.vo.Member;

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	
  @SuppressWarnings("deprecation")
  @Override
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    String servletPath = request.getServletPath();
    try {
      ServletContext sc = this.getServletContext();
      
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("session", request.getSession());
      
      Controller pageController = (Controller) sc.getAttribute(servletPath);
      
      if (pageController instanceof DataBinding) {
    	  prepareRequestData(request, model, (DataBinding) pageController);
      }
      
      String viewUrl = pageController.execute(model);
      
      for (String key : model.keySet()) {
    	  request.setAttribute(key, model.get(key));
      }
      
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9));
        return;
        
      } else {
        RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
        rd.include(request, response);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    }
  }
  
  public void prepareRequestData(HttpServletRequest request, HashMap<String, Object> model, DataBinding dataBinding) throws Exception {
	  Object[] dataBinders = dataBinding.getDataBinders();
	  String dataName = null;
	  Class<?> dataType = null;
	  Object dataObj = null;
	  for (int i = 0; i < dataBinders.length; i+=2) {
		  dataName = (String) dataBinders[i];
		  dataType = (Class<?>) dataBinders[i+1];
		  dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
		  model.put(dataName, dataObj);
	  }
  }
}
