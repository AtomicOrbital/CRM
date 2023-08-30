package CRM.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import CRM.Service.ProjectService;
import CRM.Service.StatusService;
import CRM.Service.TaskService;
import CRM.Service.UserService;
import CRM.payload.response.BaseResponse;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import ProjectCRM.Model.Project;
import ProjectCRM.Model.StatusTask;
import ProjectCRM.Model.Task;
import ProjectCRM.Model.User;

@WebServlet(name = "ApiTaskController", urlPatterns = { "/api/get-tasks", "/api/add-task", "/add-tasks",
		"/api/update-task", "/api/get-task-by-id", "/api/get-users", "/api/get-projects", "/api/get-statusTask",
		"/api/delete-task"
})
public class ApiTaskController extends HttpServlet {
	private Gson gson = new Gson();
	private TaskService taskService = new TaskService();
	private ProjectService projectService = new ProjectService();
	private UserService userService = new UserService();
	private StatusService statusService = new StatusService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		if ("/add-tasks".equals(action)) {
			List<Project> projects = projectService.getAllProjects();
			List<User> users = userService.getAllUsersWithRoles();
			List<StatusTask> statuses = statusService.getAllStatus();

			request.setAttribute("statuses", statuses);
			request.setAttribute("users", users);
			request.setAttribute("projects", projects);
			request.getRequestDispatcher("task-add.jsp").forward(request, response);
		} else if ("/api/get-tasks".equals(action)) {
			/*
			 * String taskIdStr = request.getParameter("taskId"); if(taskIdStr != null ||
			 * taskIdStr.isEmpty()) { response.setStatus(400);
			 * //response.setMessage("TaskId is required");
			 * response.getWriter().write(gson.toJson(response)); return; }
			 */
			// trả về list task
			List<Task> tasks = taskService.getTasks();
			// System.out.println("Task: " + tasks.size());
			// Set thông tin header trả về JSON
//			int taskId = Integer.parseInt(taskIdStr);
//			Task task = taskService.getTaskById(taskId);
			// Trả về 1 task
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			Gson gson = new Gson();
			String json = gson.toJson(tasks);

			// Gửi JSON string response body
			response.getWriter().write(json);
		} else if ("/api/get-task-by-id".equals(action)) {
			String taskIdStr = request.getParameter("taskId");
			if (taskIdStr == null || taskIdStr.isEmpty()) {
				response.setStatus(400);
				response.getWriter().write(gson.toJson("TaskId is required"));
				return;
			}

			int taskId = Integer.parseInt(taskIdStr);
			Task task = taskService.getTaskById(taskId);

			if (task == null) {
				response.setStatus(404);
				response.getWriter().write(gson.toJson("Task not found"));
				return;
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(task));
		} else if ("/api/get-users".equals(action)) {
			List<User> users = taskService.getAllUsers();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			Gson gson = new Gson();
			String json = gson.toJson(users);

			// Gửi json string
			response.getWriter().write(json);
		} else if ("/api/get-projects".equals(action)) {
			List<Project> projects = taskService.getAllProjects();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			Gson gson = new Gson();
			String json = gson.toJson(projects);

			// Gửi json string
			response.getWriter().write(json);
		} else if ("/api/get-statusTask".equals(action)) {
			List<StatusTask> statusTasks = taskService.getStatusTask();

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			Gson gson = new Gson();
			String json = gson.toJson(statusTasks);

			// Gửi json string
			response.getWriter().write(json);
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		if ("/api/add-task".equals(action)) {
			handleAddTask(req, resp);
		} else if ("/api/update-task".equals(action)) {
			handleUpdateTask(req, resp);
		}

	}

	public void handleAddTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BaseResponse response = new BaseResponse();
		// Đọc dữ liệu từ request
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		try {
			String taskName = req.getParameter("TaskName");
//			String startDateStr = req.getParameter("StartDate") + " 00:00:00";
			String endDateStr = req.getParameter("EndDate") + " 00:00:00";
			int executorId = -1;
			String executorIdStr = req.getParameter("ExecutorID");
			if (executorIdStr != null && !executorIdStr.isEmpty()) {
				executorId = Integer.parseInt(executorIdStr);
			}
			int projectId = Integer.parseInt(req.getParameter("ProjectID"));
			int statusId = Integer.parseInt(req.getParameter("StatusID"));

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// startDate là thời gian hiện tại (real-time)
			Timestamp startDate = new Timestamp(System.currentTimeMillis());

			// endDate là thời gian người dùng nhập vào
			java.util.Date parsedEndDate = dateFormat.parse(endDateStr);
			Timestamp endDate = new Timestamp(parsedEndDate.getTime());

			Task task = new Task();
			task.setTaskName(taskName);
			task.setStartDate(startDate);
			task.setEndDate(endDate);
			task.setExecutorId(executorId);
			task.setProjectId(projectId);
			task.setStatusId(statusId);

			System.out.println(startDate);
			System.out.println(endDate);

			boolean result = taskService.addTask(task);
			if (result) {
				response.setStatusCode(200);
				response.setMessage("Task add successfull !");
			} else {
				response.setStatusCode(400);
				response.setMessage("Task add Failed");
			}

			out.print(gson.toJson(response));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(500);
			response.setMessage("Server Error: " + e.getMessage());
			out.print(gson.toJson(response));
		}
	}

	public void handleUpdateTask(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BaseResponse response = new BaseResponse();
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		try {
			String taskName = req.getParameter("TaskName");
			String startDateStr = req.getParameter("StartDate");
			String endDateStr = req.getParameter("EndDate");
			String executorIdStr = req.getParameter("ExecutorID");
			String projectIdStr = req.getParameter("ProjectID");
			String statusIdStr = req.getParameter("StatusID");
			String taskIdStr = req.getParameter("TaskID");

			if (startDateStr != null)
				startDateStr += " 00:00:00";
			if (endDateStr != null)
				endDateStr += " 00:00:00";

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int executorId = (executorIdStr != null && !executorIdStr.isEmpty()) ? Integer.parseInt(executorIdStr) : -1;
			int projectId = (projectIdStr != null) ? Integer.parseInt(projectIdStr) : -1;
			int statusId = (statusIdStr != null) ? Integer.parseInt(statusIdStr) : -1;
			int taskId = (taskIdStr != null) ? Integer.parseInt(taskIdStr) : -1;

			java.util.Date parsedStartDate = (startDateStr != null) ? dateFormat.parse(startDateStr) : null;
			java.util.Date parsedEndDate = (endDateStr != null) ? dateFormat.parse(endDateStr) : null;

			Timestamp startDate = (parsedStartDate != null) ? new Timestamp(parsedStartDate.getTime()) : null;
			Timestamp endDate = (parsedEndDate != null) ? new Timestamp(parsedEndDate.getTime()) : null;

			Task task = new Task();
			task.setTaskName(taskName);
			task.setStartDate(startDate);
			task.setEndDate(endDate);
			task.setExecutorId(executorId);
			task.setProjectId(projectId);
			task.setStatusId(statusId);
			task.setTaskId(taskId);

			boolean result = taskService.updateTask(task);
			if (result) {
				response.setStatusCode(200);
				response.setMessage("Task update successful !");
			} else {
				response.setStatusCode(400);
				response.setMessage("Task update Failed");
			}

			out.print(gson.toJson(response));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode(500);
			response.setMessage("Server Error: " + e.getMessage());
			out.print(gson.toJson(response));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		BaseResponse response = new BaseResponse();

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		String taskIdStr = req.getParameter("TaskID");

		if (taskIdStr == null || taskIdStr.trim().isEmpty()) {
			response.setStatusCode(400);
			response.setMessage("Task is missing");
			out.print(gson.toJson(response));
			out.flush();
			return;
		}

		int taskId = Integer.parseInt(taskIdStr);

		boolean result = taskService.deleteTask(taskId);
		if (result) {
			response.setStatusCode(200);
			response.setMessage("Task deleted successfully!");
		} else {
			response.setStatusCode(500);
			response.setMessage("Failed to delete task");
		}

		out.print(gson.toJson(response));
		out.flush();
	}
}
