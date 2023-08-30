package CRM.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import CRM.Service.RoleService;
import CRM.Service.UserService;
import CRM.payload.response.BaseResponse;
import ProjectCRM.Model.Role;
import ProjectCRM.Model.User;

@WebServlet(name = "apiUserController", urlPatterns = { "/api/add-user", "/api/delete-user", "/api/update-user" })
public class ApiUserController extends HttpServlet {
	private Gson gson = new Gson();
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = req.getServletPath();

		if ("/api/update-user".equals(action)) {
			handleUpdateUser(req, resp);
		} else if ("/api/add-user".equals(action)) {
			handleAddUser(req, resp);
		} else {
			
		}
}
		
		private void handleAddUser(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		BaseResponse response = new BaseResponse();
		// Đọc dữ liệu từ request
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullName = req.getParameter("Fullname");
		String address = req.getParameter("Address");
		String phone = req.getParameter("Phone");
		int roleId = -1;

		String roleIdStr = req.getParameter("RoleID");
		if (roleIdStr != null && !roleIdStr.trim().isEmpty()) {
			try {
				roleId = Integer.parseInt(roleIdStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			response.setStatusCode(400);
			response.setMessage("Email or Password is missing");
			out.print(gson.toJson(response));
			out.flush();
			return;
		}

		// Tao doi tuong user
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setFullName(fullName);
		user.setAddress(address);
		user.setPhone(phone);
		user.setId(roleId);

		// Them nguoi dung
		boolean result = userService.addUser(user);
		if (result) {
			response.setStatusCode(200);
//				response.setMessage("User added successfully");
		} else {
			response.setStatusCode(500);
//				response.setMessage("Failed to add user");
		}

		out.print(gson.toJson(response));
		out.flush();
	}

		private void handleUpdateUser(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			BaseResponse response = new BaseResponse();

			resp.setContentType("application/json");
			PrintWriter out = resp.getWriter();

			// format kiểu dữ liệu
			String userIdStr = req.getParameter("UserID");
			System.out.println("Received UserID from client: " + userIdStr);
			if (userIdStr == null || userIdStr.trim().isEmpty()) {
				response.setStatusCode(400);
				response.setMessage("UserID is missing");
				out.print(gson.toJson(response));
				out.flush();
				return;
			}

			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String fullName = req.getParameter("Fullname");
			String address = req.getParameter("Address");
			String phone = req.getParameter("Phone");
			int roleId = -1;

			String roleIdStr = req.getParameter("RoleID");

			if (roleIdStr != null && !roleIdStr.trim().isEmpty()) {
				try {
					roleId = Integer.parseInt(roleIdStr);
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatusCode(400);
					response.setMessage("Invalid RoleID format");
					out.print(gson.toJson(response));
					out.flush();
					return;
				}
			}

			int userId;
			try {
				userId = Integer.parseInt(userIdStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.setStatusCode(400);
				response.setMessage("Invalid UserID format: " + userIdStr);
				out.print(gson.toJson(response));
				out.flush();
				return;
			}

			User user = new User();
			user.setId(userId);
			user.setEmail(email);
			user.setPassword(password);
			user.setFullName(fullName);
			user.setAddress(address);
			user.setPhone(phone);

			Role role = new Role();
			role.setRoleID(roleId);
			user.setRole(role);

			boolean result = userService.updateUser(user);
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
		
		

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		BaseResponse response = new BaseResponse();

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		String userIdStr = req.getParameter("UserID");

		if (userIdStr == null || userIdStr.trim().isEmpty()) {
			response.setStatusCode(400);
			response.setMessage("User is missing");
			out.print(gson.toJson(response));
			out.flush();
			return;
		}

		int userId = Integer.parseInt(userIdStr);

		boolean result = userService.deleteUser(userId);
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

