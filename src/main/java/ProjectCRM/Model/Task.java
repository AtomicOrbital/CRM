package ProjectCRM.Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Task {
	private int taskId;
	private String taskName;
	private Timestamp startDate;
	private Timestamp endDate;
	private int executorId;
	private int projectId;
	private int statusId;
	
	// Thêm các lớp Project, StatusTask, UserUser
	private Project project;
	private User user;
	private StatusTask statusTask;
	
	public Task() {
		
	}

	public Task(int taskId, String taskName, Timestamp startDate, Timestamp endDate, int executorId, int projectId,
			int statusId) {
		
		this.taskId = taskId;
		this.taskName = taskName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.executorId = executorId;
		this.projectId = projectId;
		this.statusId = statusId;
	}
	
	

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StatusTask getStatusTask() {
		return statusTask;
	}

	public void setStatusTask(StatusTask statusTask) {
		this.statusTask = statusTask;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getExecutorId() {
		return executorId;
	}

	public void setExecutorId(int executorId) {
		this.executorId = executorId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
}
