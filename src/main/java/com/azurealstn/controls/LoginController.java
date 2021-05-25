package main.java.com.azurealstn.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import main.java.com.azurealstn.dao.MemberDao;
import main.java.com.azurealstn.vo.Member;

public class LoginController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("loginInfo") == null) {
			return "/auth/LoginForm.jsp";
		} else {
			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member loginInfo = (Member) model.get("loginInfo");
			
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

}
