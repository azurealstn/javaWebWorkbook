package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.dao.ProjectDao;

@Component("/project/list.do")
public class ProjectListController implements Controller {
  ProjectDao projectDao;
  
  public ProjectListController setMemberDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {
    model.put("projects", projectDao.selectList());
    return "/project/ProjectList.jsp";
  }
}
