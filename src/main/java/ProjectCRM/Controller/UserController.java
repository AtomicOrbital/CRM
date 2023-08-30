package ProjectCRM.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CRM.Service.UserService;
import ProjectCRM.Model.User;

@WebServlet(name = "AllUserRole", urlPatterns = { "/user-table" })
public class UserController extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<User> users = userService.getAllUsersWithRoles();
			
			 for(User user : users) { 
				 System.out.println("user : " + user.getPassword()); 
			 }
			 
			request.setAttribute("users", users);
			request.getRequestDispatcher("user-table.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
