package CRM.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import CRM.Service.ProjectService;
import CRM.payload.response.BaseResponse;
import java.util.*;
import ProjectCRM.Model.Project;

@WebServlet(name = "ApiProjectController", urlPatterns = {"/add-project", "/get-projects", "/api/update-project", 
		"/api/delete-project", "/api/add-project"})
public class ApiProjectController extends HttpServlet {
	private ProjectService projectService = new ProjectService();
	private Gson gson = new Gson();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String path = request.getServletPath();
		if(path.equals("/add-project")) {
			request.getRequestDispatcher("groupwork-add.jsp").forward(request, response);
		} else if(path.equals("/get-projects")) {
			List<Project> projects = projectService.getAllProjects();
			request.setAttribute("projects", projects);
			request.getRequestDispatcher("groupwork.jsp").forward(request, response);
		}		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();
		
		if("/api/update-project".equals(action)) {
			handlUpdateProject(req, resp);
		} else if("/api/add-project".equals(action)) {
			handlAddProject(req, resp);
		}		
	}
		
	public void handlUpdateProject(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException,IOException{
		BaseResponse response = new BaseResponse();
		// Đọc dữ liệu từ request
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		
		String projectName = req.getParameter("ProjectName");
		String startDateStr = req.getParameter("StartDate");
		String endDateStr = req.getParameter("EndDate");
		
		Date startDate = null;
		Date endDate = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = sdf.parse(startDateStr);
			endDate = sdf.parse(endDateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		int projectId = -1;
		String projectIdStr = req.getParameter("ProjectID");
		if(projectIdStr != null && !projectIdStr.trim().isEmpty()) {
			try {
				projectId = Integer.parseInt(projectIdStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Project project = new Project();
		project.setProjectId(projectId);
		project.setProjectName(projectName);
		project.setStartDate(startDate);
		project.setEndDate(endDate);
		System.out.println("ProjectID: " + projectId);
		System.out.println("ProjectName: " + projectName);
		System.out.println("StartDate: " + sdf.format(startDate));
		System.out.println("EndDate: " + sdf.format(endDate));
		boolean result = projectService.updateProject(project);
		if (result) {
			response.setStatusCode(200);
			response.setMessage("User updated Successfully");
		} else {
			response.setStatusCode(400);
			response.setMessage("Failed to update User");
		}

		out.print(gson.toJson(response));
		out.flush();
	}
	
	public void handlAddProject(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		BaseResponse response = new BaseResponse();
		// Đọc dữ liệu từ request
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		
		String projectName = req.getParameter("ProjectName");
		String startDateStr = req.getParameter("StartDate");
		String endDateStr = req.getParameter("EndDate");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date startDate = formatter.parse(startDateStr);
			Date endDate = formatter.parse(endDateStr);
			
			Project project = new Project();
			project.setProjectName(projectName);
			project.setStartDate(startDate);
			project.setEndDate(endDate);
			
			System.out.println("ProjectName: " + projectName);
			System.out.println("StartDate: " + startDateStr);
			System.out.println("EndDate: " + endDateStr);

			
			boolean result = projectService.addProject(project);
			if(result) {
				response.setStatusCode(200);
				response.setMessage("Project add successfull !");
			} else {
				response.setStatusCode(400);
				response.setMessage("Project add Failed");
			}
			out.print(gson.toJson(response));
			out.flush();
		} catch (Exception e) {
			response.setMessage("error");
			response.setMessage("Invalid date format");
			out.print(gson.toJson(response));
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		BaseResponse response = new BaseResponse();

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		String projectIdStr = req.getParameter("ProjectID");

		if (projectIdStr == null || projectIdStr.trim().isEmpty()) {
			response.setStatusCode(400);
			response.setMessage("User is missing");
			out.print(gson.toJson(response));
			out.flush();
			return;
		}

		int projectId = Integer.parseInt(projectIdStr);

		boolean result = projectService.deleteProject(projectId);
		if (result) {
			response.setStatusCode(200);
			response.setMessage("User deleted successfully!");
		} else {
			response.setStatusCode(500);
			response.setMessage("Failed to delete user");
		}

		out.print(gson.toJson(response));
		out.flush();
	}
}
