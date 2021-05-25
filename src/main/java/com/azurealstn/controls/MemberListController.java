package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.dao.MySqlMemberDao;

public class MemberListController implements Controller {
	MySqlMemberDao memberDao;
	
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}

}
