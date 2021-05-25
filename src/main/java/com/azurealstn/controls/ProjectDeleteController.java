package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.dao.ProjectDao;
import main.java.com.azurealstn.vo.Member;
import main.java.com.azurealstn.vo.Project;

@Component("/project/delete.do")
public class ProjectDeleteController implements Controller, DataBinding {
	ProjectDao projectDao;

	public ProjectDeleteController setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}

	public Object[] getDataBinders() {
		return new Object[] { "no", Integer.class };
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer no = (Integer) model.get("no");
		projectDao.delete(no);

		return "redirect:list.do";
	}
}
