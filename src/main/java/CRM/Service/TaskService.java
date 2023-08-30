package CRM.Service;

import java.util.List;

import CRM.Repository.StatusReposiroty;
import CRM.Repository.TaskRepository;
import ProjectCRM.Model.Project;
import ProjectCRM.Model.StatusTask;
import ProjectCRM.Model.Task;

import ProjectCRM.Model.User;
public class TaskService {
	public boolean addTask(Task task) {
		return TaskRepository.addTask(task);
	}
	
	public List<Task> getTasks(){
		return TaskRepository.getTasks();
	}
	
	public boolean updateTask(Task task) {
		return TaskRepository.updateTask(task);
	}
	
	public Task getTaskById(int taskId) {
		return TaskRepository.getTaskById(taskId);
	}
	
	public List<User> getAllUsers(){
		return TaskRepository.getAllUsers();
	}
	
	public List<Project> getAllProjects(){
		return TaskRepository.getAllProjects();
	}
	
	public List<StatusTask> getStatusTask(){
		return TaskRepository.getAllStatusTask();
	}
	
	public boolean deleteTask(int taskId) {
		return TaskRepository.deleteTask(taskId);
	}
}
