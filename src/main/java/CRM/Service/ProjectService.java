package CRM.Service;

import java.util.List;

import CRM.Repository.ProjectRepository;
import ProjectCRM.Model.Project;

public class ProjectService {
	public boolean addProject(Project project) {
		return ProjectRepository.addProject(project);
	}
	
	public List<Project>getAllProjects(){
		return ProjectRepository.getAllProjects();
	}
	
	public boolean updateProject(Project project) {
		return ProjectRepository.updateProject(project);
	}
	
	public boolean deleteProject(int projectId) {
		return ProjectRepository.deleteProject(projectId);
	}
}
