package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.vo.Member;


@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		
		if (member.getEmail() == null) {
			Integer no = (Integer) model.get("no");
			Member detailInfo = memberDao.selectOne(no);
			model.put("member", detailInfo);
			return "/member/MemberUpdateForm.jsp";
		} else {
			memberDao.update(member);
			return "redirect:list.do";
		}
		
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class,
			"member", main.java.com.azurealstn.vo.Member.class
		};
	}
}
