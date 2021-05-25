package main.java.com.azurealstn.controls;

import java.util.Map;

import main.java.com.azurealstn.annotation.Component;
import main.java.com.azurealstn.bind.DataBinding;
import main.java.com.azurealstn.dao.MySqlMemberDao;
import main.java.com.azurealstn.dao.ProjectDao;
import main.java.com.azurealstn.vo.Member;
import main.java.com.azurealstn.vo.Project;


@Component("/project/add.do")
public class ProjectAddController implements Controller, DataBinding {
  ProjectDao projectDao;
  
  public ProjectAddController setProjectDao(ProjectDao projectDao) {
    this.projectDao = projectDao;
    return this;
  }
  
  public Object[] getDataBinders() {
    return new Object[]{
        "project", main.java.com.azurealstn.vo.Project.class
    };
  }
  
  @Override
  public String execute(Map<String, Object> model) throws Exception {
    Project project = (Project)model.get("project");
    if (project.getTitle() == null) {
      return "/project/ProjectForm.jsp";
      
    } else {
      projectDao.insert(project);
      return "redirect:list.do";
    }
  }
}
