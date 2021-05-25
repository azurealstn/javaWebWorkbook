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
      model.put("memberDao", sc.getAttribute("memberDao"));
      model.put("session", request.getSession());
      
      Controller pageController = null;
      
      if ("/member/list.do".equals(servletPath)) {
    	  pageController = new MemberListController();
      } else if ("/member/add.do".equals(servletPath)) {
        pageController = new MemberAddController();
        if (request.getParameter("email") != null) {
          model.put("member", new Member()
            .setEmail(request.getParameter("email"))
            .setPassword(request.getParameter("password"))
            .setName(request.getParameter("name")));
        }
      } else if ("/member/update.do".equals(servletPath)) {
          pageController = new MemberUpdateController();
          if (request.getParameter("email") != null) {
            model.put("member", new Member()
              .setNo(Integer.parseInt(request.getParameter("no")))
              .setEmail(request.getParameter("email"))
              .setName(request.getParameter("name")));
          } else {
            model.put("no", Integer.valueOf(request.getParameter("no")));
          }
      } else if ("/member/delete.do".equals(servletPath)) {
        pageController = new MemberDeleteController();
        model.put("no", new Integer(request.getParameter("no")));
      } else if ("/auth/login.do".equals(servletPath)) {
        pageController = new LoginController();
        if (request.getParameter("email") != null) {
            model.put("loginInfo", new Member()
              .setEmail(request.getParameter("email"))
              .setPassword(request.getParameter("password")));
        }
      } else if ("/auth/logout.do".equals(servletPath)) {
        pageController = new LogoutController();
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
}
