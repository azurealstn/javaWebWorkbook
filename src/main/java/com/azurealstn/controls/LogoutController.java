package main.java.com.azurealstn.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import main.java.com.azurealstn.annotation.Component;


@Component("/auth/logout.do")
public class LogoutController implements Controller {

	@Override
	  public String execute(Map<String, Object> model) throws Exception {
	    HttpSession session = (HttpSession)model.get("session");
	    session.invalidate();
	    
	    return "redirect:login.do";
	  }
}
