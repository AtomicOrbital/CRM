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
@WebServlet(name = "RoleController", urlPatterns = {"/role-add", "/role-table"})

public class RoleController extends HttpServlet {
	private RoleService roleService = new RoleService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		
		if("/role-add".equals(path)) {		
			request.getRequestDispatcher("role-add.jsp").forward(request, response);
		} else if("/role-table".equals(path)) {
			List<Role> roles = roleService.getAllRoles();
//			System.out.println("Number Roles: " + roles.size());
			request.setAttribute("rolesList", roles);
			request.getRequestDispatcher("role-table.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		String path = request.getServletPath(); // lấy đường dẫn yêu cầu
		if("/role-add".equals(path)) {
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
			request.getRequestDispatcher("role-add.jsp").forward(request, response);
		} 
	}

}
