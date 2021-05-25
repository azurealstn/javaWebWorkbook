package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.dao.MemberDao;
import main.java.com.azurealstn.vo.Member;

public class MemberUpdateController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		
		if (model.get("member") == null) {
			Integer no = (Integer) model.get("no");
			Member member = memberDao.selectOne(no);
			System.out.println(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		} else {
			Member member = (Member) model.get("member");
			memberDao.update(member);
			return "redirect:list.do";
		}
		
	}
}