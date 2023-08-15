package ProjectCRM.Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import CRM.Service.RoleService;
import ProjectCRM.Model.Role;

@WebFilter(filterName = "/RoleTableFilter", urlPatterns = "/role-table.jsp")
public class RoleTableFilter extends HttpFilter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		List<Role> roles = new RoleService().getAllRoles();
		System.out.println("Number Roles: " + roles.size());
		request.setAttribute("rolesList", roles);
		chain.doFilter(request, response);
	}
}
