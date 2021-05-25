package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.MySqlMemberDao;


@Component("/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		Integer no = (Integer) model.get("no");
		memberDao.delete(no);
		
		return "redirect:list.do";
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class
		};
	}

	
}
