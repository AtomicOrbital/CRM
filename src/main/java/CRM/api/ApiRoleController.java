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
import CRM.payload.response.BaseResponse;
import ProjectCRM.Model.Role;

@WebServlet(name = "apiRoleController", urlPatterns = { "/api/role/delete", "/api/role/edit-role" })
public class ApiRoleController extends HttpServlet {
	private Gson gson = new Gson();
	private RoleService roleService = new RoleService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr != null && !idStr.trim().isEmpty()) {
			try {
				int id = Integer.parseInt(idStr);

				boolean isSuccess = roleService.deleteRole(id);

				BaseResponse response = new BaseResponse();
				response.setStatusCode(200);
				response.setMessage(isSuccess ? "Xóa thành công " : "Xóa thất bại");
				response.setData(isSuccess);

				String json = gson.toJson(response);

				resp.setCharacterEncoding("UTF-8");

				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");

				out.print(json);
				out.flush();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BaseResponse response = new BaseResponse();

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
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
		String roleName = req.getParameter("RoleName");
		String description = req.getParameter("Description");

		Role role = new Role();
		role.setRoleName(roleName);
		role.setDescription(description);
		role.setRoleID(roleId);

		boolean result = roleService.updateRole(role);
		if (result) {
			response.setStatusCode(200);
			response.setMessage("Role Updated Successfully");
		} else {
			response.setStatusCode(400);
			response.setMessage("Role Update Failed");
		}
		out.print(gson.toJson(response));
		out.flush();
	}
}
