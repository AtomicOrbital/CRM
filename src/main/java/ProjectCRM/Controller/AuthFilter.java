package ProjectCRM.Controller;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		String path = request.getServletPath();
		if (path.equals("/login.jsp") || path.equals("/login")) {
			chain.doFilter(request, response);
			return;
		}
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user")) {
					chain.doFilter(request, response); // User đã đăng nhập, cho phép truy cập
					return;
				}
			}
		}
		
		response.sendRedirect(request.getContextPath() + "/login.jsp"); // Chưa đăng nhập, chuyển hướng đến trang đăng nhập
	}
}
