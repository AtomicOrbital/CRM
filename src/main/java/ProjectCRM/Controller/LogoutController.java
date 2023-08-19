package ProjectCRM.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
@WebServlet(name = "/LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Cookie emailCookie = new Cookie("userEmail", "");
		Cookie passwordCookie = new Cookie("userPassword", "");
		
		emailCookie.setMaxAge(0); // Dat thoi gian cho cookie bang 0
		passwordCookie.setMaxAge(0); // Dat thoi gian cho cookie bang 0
		
		response.addCookie(emailCookie);
		response.addCookie(passwordCookie);
		
		response.sendRedirect("login.jsp");
	}

	

}
