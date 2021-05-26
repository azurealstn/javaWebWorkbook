package main.java.com.azurealstn.controls;

import java.util.HashMap;
import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.ProjectDao;

@Component("/project/list.do")
public class ProjectListController implements Controller, DataBinding {
  ProjectDao projectDao;
  
  public ProjectListController setMemberDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
    return this;
  }
  
  @Override
  public Object[] getDataBinders() {
	  return new Object[] {
			  "orderCond", String.class
	  };
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
	HashMap<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put("orderCond", model.get("orderCond"));
	
    model.put("projects", projectDao.selectList(paramMap));
    return "/project/ProjectList.jsp";
  }
}
