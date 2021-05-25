package main.java.com.azurealstn.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.vo.Member;


@Component("/auth/login.do")
public class LoginController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public LoginController setMemberDao(MySqlMemberDao memberDao) {
	    this.memberDao = memberDao;
	    return this;
	} 

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member loginInfo = (Member) model.get("loginInfo");
		
		if (loginInfo.getEmail() == null) {
			return "/auth/LoginForm.jsp";
		} else {
			Member member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			
			if (member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LoginFail.jsp";
			}
		}
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"loginInfo", main.java.com.azurealstn.vo.Member.class
		};
	}

}
