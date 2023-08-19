package CRM.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName =  "/CustomerFilter", urlPatterns = {"/roles"})
public class CustomerFilter extends HttpFilter {
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		String  roleName = (String) session.getAttribute("roleName");
		
		if(roleName != null && roleName.toUpperCase().equals("ADMIN")) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
