package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.vo.Member;


@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
	    this.memberDao = memberDao;
	    return this;
	} 

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		if (member.getEmail() == null) {
			return "/member/MemberForm.jsp";
		} else {
			memberDao.insert(member);
			return "redirect:list.do";
		}
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"member", main.java.com.azurealstn.vo.Member.class
		};
	}

}
