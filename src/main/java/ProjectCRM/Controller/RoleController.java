package ProjectCRM.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ProjectCRM.Model.Role;
import CRM.Service.RoleService;
@WebServlet(name = "RoleController", urlPatterns = {"/roles", "/role-table"})

public class RoleController extends HttpServlet {
	private RoleService roleService = new RoleService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String roleName = request.getParameter("roleName");
		String description = request.getParameter("description");
		
		Role role = new Role();
		role.setRoleName(roleName);
		role.setDescription(description);
		
		String message = roleService.addRole(role);
		System.out.println("Message: " + message);
		request.setAttribute("messages", message);
		request.getRequestDispatcher("/role-add.jsp").forward(request, response);

	}

}